package pro.sky.tms_app.dto;

import lombok.Data;
import pro.sky.tms_app.entity.TaskPriority;
import pro.sky.tms_app.entity.TaskStatus;

import java.util.List;

@Data
public class TaskWithCommDTO {

    private Long id;

    private String header;

    private TaskStatus status;

    private TaskPriority priority;

    private Long authorId;

    private Long executorId;

    private String description;

    private List<CommDTO> commDtoList;

}
