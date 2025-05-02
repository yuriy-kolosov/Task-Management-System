package pro.sky.tms_app.service.impl;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import pro.sky.tms_app.dto.CommDTO;
import pro.sky.tms_app.dto.TaskDTO;
import pro.sky.tms_app.dto.TaskWithCommDTO;
import pro.sky.tms_app.entity.CommEntity;
import pro.sky.tms_app.entity.TaskEntity;
import pro.sky.tms_app.entity.TaskStatus;
import pro.sky.tms_app.entity.UzerEntity;
import pro.sky.tms_app.exception.InvalidUserException;
import pro.sky.tms_app.exception.UserNotFoundException;
import pro.sky.tms_app.mapper.CommMapper;
import pro.sky.tms_app.mapper.TaskMapper;
import pro.sky.tms_app.repository.CommRepository;
import pro.sky.tms_app.repository.TaskRepository;
import pro.sky.tms_app.repository.UzerRepository;
import pro.sky.tms_app.service.UzerService;

import java.util.List;

import static pro.sky.tms_app.entity.UzerRole.ROLE_ADMIN;

/**
 * Class that implements the Uzer service
 *
 * @author Yuriy_Kolosov
 */
@Service
@Data
@RequiredArgsConstructor
public class UzerServiceImpl implements UzerService {

    public final CommRepository commRepository;
    public final TaskRepository taskRepository;
    public final UzerRepository uzerRepository;

    /**
     * Method to read information about all user tasks -
     * where user is task's author and/or task's executor -
     * Repository method {@link UzerRepository#findByEmail(String) uzerRepository.findByEmail(userName)} is using -
     * Repository method {@link TaskRepository#findAll() taskRepository.findAll()} is using
     *
     * @param userName       username in email format (task's author and/or task's executor)
     * @param authentication request's applicant (task's author and/or task's executor) data
     * @return all user tasks data
     */
    @Override
    public List<TaskDTO> findAllUserTasks(String userName, Authentication authentication) {

        UzerEntity uzerEntity = uzerRepository.findByEmail(userName)
                .orElseThrow(() -> new UserNotFoundException("Пользователь отсутствует в базе данных"));
        Long userId = uzerEntity.getId();

        UzerEntity applicantEntity = uzerRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new UserNotFoundException("Пользователь отсутствует в базе данных"));

        if ((applicantEntity.getRole() == ROLE_ADMIN) | (applicantEntity.getId() == userId)) {

            List<TaskEntity> taskEntityList = taskRepository.findAll();

            List<TaskDTO> taskDtoList = taskEntityList.stream()
                    .filter((a) -> (a.getAuthor().getId() == userId) | (a.getExecutorId() == userId))
                    .map(TaskMapper.INSTANCE::toDto)
                    .toList();

            for (TaskDTO taskDTO : taskDtoList) {
                for (TaskEntity taskEntity : taskEntityList) {
                    if (taskDTO.getId().equals(taskEntity.getId())) {
                        taskDTO.authorIdToDto(taskDTO, taskEntity.getAuthor().getId());
                    }
                }
            }

            return taskDtoList;
        } else {
            throw new InvalidUserException("Пользователь не является администратором (автором) или исполнителем задач");
        }

    }

    /**
     * Method to read information about all user tasks (by pages) -
     * where user is task's author and/or task's executor -
     * Repository method {@link UzerRepository#findByEmail(String) uzerRepository.findByEmail(userName)} is using -
     * Repository method {@link TaskRepository#findPages(PageRequest) taskRepository.findPages(taskPages)} is using
     *
     * @param userName       username in email format (task's author and/or task's executor)
     * @param pageNumber     page number
     * @param pageAmount     page amount
     * @param authentication request's applicant (task's author and/or task's executor) data
     * @return all user tasks data (by pages)
     */
    @Override
    public List<TaskDTO> findAllUserTasksByPages(String userName
            , int pageNumber, int pageAmount, Authentication authentication) {

        UzerEntity uzerEntity = uzerRepository.findByEmail(userName)
                .orElseThrow(() -> new UserNotFoundException("Пользователь отсутствует в базе данных"));
        Long userId = uzerEntity.getId();

        UzerEntity applicantEntity = uzerRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new UserNotFoundException("Пользователь отсутствует в базе данных"));

        if ((applicantEntity.getRole() == ROLE_ADMIN) | (applicantEntity.getId() == userId)) {

            List<TaskDTO> taskDtoList;

            PageRequest taskPages = PageRequest.of(pageNumber - 1, pageAmount);
            List<TaskEntity> taskEntityList = taskRepository.findPages(taskPages);

            taskDtoList = taskEntityList.stream()
                    .filter((a) -> (a.getAuthor().getId() == userId) | (a.getExecutorId() == userId))
                    .map(TaskMapper.INSTANCE::toDto)
                    .toList();

            if (!taskDtoList.isEmpty()) {
                for (TaskDTO taskDTO : taskDtoList) {
                    for (TaskEntity taskEntity : taskEntityList) {
                        if (taskDTO.getId().equals(taskEntity.getId())) {
                            taskDTO.authorIdToDto(taskDTO, taskEntity.getAuthor().getId());
                        }
                    }
                }

            }
            return taskDtoList;

        } else {
            throw new InvalidUserException("Пользователь не является администратором (автором) или исполнителем задач");
        }

    }

    /**
     * Method to update task's status -
     * Repository method {@link UzerRepository#findByEmail(String) uzerRepository.findByEmail(userName)} is using -
     * Repository method {@link TaskRepository#findById(Object) taskRepository.findById(id)} is using -
     * Repository method {@link TaskRepository#save(Object) taskRepository.save(taskEntity} is using
     *
     * @param taskId         task's id that must be updated
     * @param taskStatus     task's status
     * @param authentication request's applicant (task's author and/or task's executor) data
     * @return task updated data
     */
    @Override
    public TaskDTO updateTaskStatus(Long taskId, TaskStatus taskStatus, Authentication authentication) {

        UzerEntity applicantEntity = uzerRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new UserNotFoundException("Пользователь отсутствует в базе данных"));

        TaskEntity taskEntity = taskRepository.findById(taskId)
                .orElseThrow(() -> new UserNotFoundException("Задача не найдена в базе данных"));

        if ((applicantEntity.getRole() == ROLE_ADMIN) | (taskEntity.getExecutorId() == applicantEntity.getId())) {

            taskEntity.setStatus(taskStatus);
            taskRepository.save(taskEntity);

            TaskDTO taskDTO = TaskMapper.INSTANCE.toDto(taskEntity);
            taskDTO.authorIdToDto(taskDTO, taskEntity.getAuthor().getId());

            return taskDTO;

        } else {
            throw new InvalidUserException("Пользователь не является администратором (автором) или исполнителем данной задачи");
        }

    }

    /**
     * Method to create task's comment -
     * Repository method {@link UzerRepository#findByEmail(String) uzerRepository.findByEmail(userName)} is using -
     * Repository method {@link TaskRepository#findById(Object) taskRepository.findById(id)} is using -
     * Repository method {@link CommRepository#save(Object) commRepository.save(commEntity)} is using
     *
     * @param commDto        task's comment data created
     * @param authentication request's applicant (task's author and/or task's executor) data
     * @return task's comment created data
     */
    @Override
    public CommDTO addCommentToTask(CommDTO commDto, Authentication authentication) {

        UzerEntity uzerEntity = uzerRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new UserNotFoundException("Пользователь отсутствует в базе данных"));
        TaskEntity taskEntity = taskRepository.findById(commDto.getTaskId())
                .orElseThrow(() -> new UserNotFoundException("Задача не найдена в базе данных"));

        if ((uzerEntity.getRole() == ROLE_ADMIN) | (taskEntity.getExecutorId() == uzerEntity.getId())) {

            CommEntity commEntity = CommMapper.INSTANCE.toEntity(commDto);
            commEntity.setTask(taskEntity);
            commRepository.save(commEntity);

            CommDTO commDTOAdded = CommMapper.INSTANCE.toDto(commEntity);
            commDTOAdded.taskIdToDto(commDTOAdded, taskEntity.getId());

            return commDTOAdded;

        } else {
            throw new InvalidUserException("Пользователь не является администратором или исполнителем данной задачи");
        }
    }

    /**
     * Method to read information about user task -
     * where user is task's author and/or task's executor -
     * Repository method {@link UzerRepository#findByEmail(String) uzerRepository.findByEmail(userName)} is using -
     * Repository method {@link TaskRepository#findById(Object) taskRepository.findById(id)} is using -
     * Repository method {@link CommRepository#findAll() taskRepository.findAll()} is using
     *
     * @param taskId         task's id
     * @param authentication request's applicant (task's author and/or task's executor) data
     * @return user's task data
     */
    @Override
    public TaskWithCommDTO findUserTaskWithComments(Long taskId, Authentication authentication) {

        UzerEntity uzerEntity = uzerRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new UserNotFoundException("Пользователь отсутствует в базе данных"));
        TaskEntity taskEntity = taskRepository.findById(taskId)
                .orElseThrow(() -> new UserNotFoundException("Задача не найдена в базе данных"));

        if ((uzerEntity.getRole() == ROLE_ADMIN) | (taskEntity.getExecutorId() == uzerEntity.getId())) {

            List<CommEntity> commEntityList = commRepository.findAll();

            List<CommDTO> commDtoList = commEntityList.stream()
                    .filter((t) -> t.getTask().getId() == taskId)
                    .map(CommMapper.INSTANCE::toDto)
                    .peek((t) -> t.taskIdToDto(t, taskId))
                    .toList();

            TaskWithCommDTO taskWithCommDto = new TaskWithCommDTO();
            TaskDTO taskDto = TaskMapper.INSTANCE.toDto(taskEntity);
            taskDto.authorIdToDto(taskDto, taskEntity.getAuthor().getId());

            taskWithCommDto.setId(taskDto.getId());
            taskWithCommDto.setHeader(taskDto.getHeader());
            taskWithCommDto.setStatus(taskDto.getStatus());
            taskWithCommDto.setPriority(taskDto.getPriority());
            taskWithCommDto.setAuthorId(taskDto.getAuthorId());
            taskWithCommDto.setExecutorId(taskDto.getExecutorId());
            taskWithCommDto.setDescription(taskDto.getDescription());

            taskWithCommDto.setCommDtoList(commDtoList);

            return taskWithCommDto;

        } else {
            throw new InvalidUserException("Пользователь не является администратором или исполнителем данной задачи");
        }

    }

}
