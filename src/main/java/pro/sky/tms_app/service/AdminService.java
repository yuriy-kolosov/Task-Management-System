package pro.sky.tms_app.service;

import org.springframework.security.core.Authentication;
import pro.sky.tms_app.dto.TaskDTO;
import pro.sky.tms_app.dto.UzerDTO;

import java.util.List;

public interface AdminService {

    UzerDTO findUserInfo(String userName);

    List<TaskDTO> findAllTasksByPages(int pageNumber, int pageAmount, Authentication authentication);

    TaskDTO saveTask(TaskDTO taskDTO, Authentication authentication);

    TaskDTO updateTask(TaskDTO taskDTO, Authentication authentication);

    TaskDTO deleteTask(Long taskId, Authentication authentication);

}
