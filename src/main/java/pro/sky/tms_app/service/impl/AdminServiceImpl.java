package pro.sky.tms_app.service.impl;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import pro.sky.tms_app.dto.TaskDTO;
import pro.sky.tms_app.dto.UzerDTO;
import pro.sky.tms_app.entity.TaskEntity;
import pro.sky.tms_app.entity.UzerEntity;
import pro.sky.tms_app.exception.InvalidUserException;
import pro.sky.tms_app.exception.TaskNotFoundException;
import pro.sky.tms_app.exception.UserNotFoundException;
import pro.sky.tms_app.mapper.TaskMapper;
import pro.sky.tms_app.repository.TaskRepository;
import pro.sky.tms_app.repository.UzerRepository;
import pro.sky.tms_app.service.AdminService;

import java.util.List;

import static pro.sky.tms_app.entity.UzerRole.ROLE_ADMIN;

/**
 * Class that implements the Admin service
 *
 * @author Yuriy_Kolosov
 */
@Service
@Data
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final TaskRepository taskRepository;
    private final UzerRepository uzerRepository;

    /**
     * Method to read information about a user with name as userName -
     * Repository method {@link UzerRepository#findByEmail(String) uzerRepository.findByEmail(userName)} is using
     *
     * @param userName username in email format
     * @return user data
     */
    @Override
    public UzerDTO findUserInfo(String userName) {

        UzerEntity uzerEntity = uzerRepository.findByEmail(userName)
                .orElseThrow(() -> new UserNotFoundException("Пользователь отсутствует в базе данных"));
        UzerDTO uzerDTO = new UzerDTO();
        uzerDTO.setId(uzerEntity.getId());
        uzerDTO.setEmail(uzerEntity.getEmail());
        uzerDTO.setRole(uzerEntity.getRole());

        return uzerDTO;

    }

    /**
     * Method to read information about all tasks (by pages) -
     * where user is this request applicant and a task's author -
     * Repository method {@link UzerRepository#findByEmail(String) uzerRepository.findByEmail(userName)} is using -
     * Repository method {@link TaskRepository#findPages(PageRequest) taskRepository.findPages(taskPages)} is using
     *
     * @param pageNumber     page number
     * @param pageAmount     page amount
     * @param authentication request's applicant (task's author) data
     * @return all user tasks data (by pages)
     */
    @Override
    public List<TaskDTO> findAllTasksByPages(int pageNumber, int pageAmount, Authentication authentication) {

        UzerEntity applicantEntity = uzerRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new UserNotFoundException("Пользователь отсутствует в базе данных"));
        Long applicantId = applicantEntity.getId();

        if (applicantEntity.getRole() == ROLE_ADMIN) {

            PageRequest taskPages = PageRequest.of(pageNumber - 1, pageAmount);
            List<TaskEntity> taskEntityList = taskRepository.findPages(taskPages);

            List<TaskDTO> taskDtoList = taskEntityList.stream()
                    .map(TaskMapper.INSTANCE::toDto)
                    .toList();

            for (int i = 0; i < taskEntityList.size(); i++) {

                taskDtoList.get(i).authorIdToDto(taskDtoList.get(i), taskEntityList.get(i).getAuthor().getId());

            }

            return taskDtoList;
        } else {
            throw new InvalidUserException("Пользователь не является администратором");
        }

    }

    /**
     * Method to create a new task with request applicant (user) as this task's author -
     * Repository method {@link UzerRepository#findByEmail(String) uzerRepository.findByEmail(userName)} is using -
     * Repository method {@link TaskRepository#save(Object) uzerRepository.save(taskEntity)} is using -
     *
     * @param taskDTO        task's data to it's created
     * @param authentication request's applicant (task's author) data
     * @return task created data
     */
    @Override
    public TaskDTO saveTask(TaskDTO taskDTO, Authentication authentication) {

        UzerEntity uzerEntity = uzerRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new UserNotFoundException("Пользователь отсутствует в базе данных"));

        TaskEntity taskEntity = TaskMapper.INSTANCE.toEntity(taskDTO);
        taskEntity.setAuthor(uzerEntity);
        taskRepository.save(taskEntity);

        TaskDTO newTaskDto = TaskMapper.INSTANCE.toDto(taskEntity);
        newTaskDto.setAuthorId(uzerEntity.getId());

        return taskDTO;

    }

    /**
     * Method to update task -
     * Repository method {@link UzerRepository#findByEmail(String) uzerRepository.findByEmail(userName)} is using -
     * Repository method {@link UzerRepository#findById(Object)  uzerRepository.findById(id)} is using -
     * Repository method {@link TaskRepository#findById(Object) taskRepository.findById(id)} is using -
     * Repository method {@link TaskRepository#save(Object) uzerRepository.save(taskEntity)} is using
     *
     * @param taskDTO        task's data to it's updated
     * @param authentication request's applicant data
     * @return task updated data
     */
    @Override
    public TaskDTO updateTask(TaskDTO taskDTO, Authentication authentication) {

        UzerEntity applicantEntity = uzerRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new UserNotFoundException("Пользователь отсутствует в базе данных"));

        TaskEntity taskEntity = taskRepository.findById(taskDTO.getId())
                .orElseThrow(() -> new TaskNotFoundException("Задача не найдена в базе данных"));

        UzerEntity executorEntityUpdated = uzerRepository.findById(taskDTO.getExecutorId())
                .orElseThrow(() -> new UserNotFoundException("Новый исполнитель задачи отсутствует в базе данных"));

        if ((applicantEntity.getRole() == ROLE_ADMIN)) {

            TaskEntity taskEntityUpdated = TaskMapper.INSTANCE.toEntity(taskDTO);
            taskEntityUpdated.setAuthor(taskEntity.getAuthor());        // id автора задачи изменению не подлежит
            taskRepository.save(taskEntityUpdated);

            TaskDTO taskDTOUpdated = TaskMapper.INSTANCE.toDto(taskEntityUpdated);
            taskDTOUpdated.authorIdToDto(taskDTOUpdated, taskEntity.getAuthor().getId());

            return taskDTOUpdated;

        } else {
            throw new InvalidUserException("Пользователь не является администратором");
        }

    }

    /**
     * Method to delete task -
     * Repository method {@link UzerRepository#findByEmail(String) uzerRepository.findByEmail(userName)} is using -
     * Repository method {@link TaskRepository#findById(Object) taskRepository.findById(id)} is using -
     * Repository method {@link TaskRepository#delete(Object) taskRepository.delete(taskEntity)} is using
     *
     * @param taskId         task's id that must be deleted
     * @param authentication request's applicant data
     * @return task deleted data
     */
    @Override
    public TaskDTO deleteTask(Long taskId, Authentication authentication) {

        UzerEntity applicantEntity = uzerRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new UserNotFoundException("Пользователь отсутствует в базе данных"));

        TaskEntity taskEntity = taskRepository.findById(taskId)
                .orElseThrow(() -> new UserNotFoundException("Задача не найдена в базе данных"));

        if ((applicantEntity.getRole() == ROLE_ADMIN)) {

            TaskDTO taskDTODeleted = TaskMapper.INSTANCE.toDto(taskEntity);
            taskDTODeleted.authorIdToDto(taskDTODeleted, taskEntity.getAuthor().getId());

            taskRepository.delete(taskEntity);

            return taskDTODeleted;

        } else {
            throw new InvalidUserException("Пользователь не является администратором");
        }

    }

}
