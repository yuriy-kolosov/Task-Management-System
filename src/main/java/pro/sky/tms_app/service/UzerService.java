package pro.sky.tms_app.service;

import org.springframework.security.core.Authentication;
import pro.sky.tms_app.dto.CommDTO;
import pro.sky.tms_app.dto.TaskDTO;
import pro.sky.tms_app.dto.TaskWithCommDTO;
import pro.sky.tms_app.entity.TaskStatus;

import java.util.List;

public interface UzerService {

    List<TaskDTO> findAllUserTasks(String userName, Authentication authentication);

    List<TaskDTO> findAllUserTasksByPages(String userName, int pageNumber, int pageAmount
            , Authentication authentication);

    TaskDTO updateTaskStatus(Long taskId, TaskStatus taskStatus, Authentication authentication);

    CommDTO addCommentToTask(CommDTO commDto, Authentication authentication);

    TaskWithCommDTO findUserTaskWithComments(Long taskId, Authentication authentication);

}
