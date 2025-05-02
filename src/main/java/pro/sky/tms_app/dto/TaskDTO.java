package pro.sky.tms_app.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import pro.sky.tms_app.entity.TaskPriority;
import pro.sky.tms_app.entity.TaskStatus;

@Data
public class TaskDTO {

    @NotNull
    private Long id;

    @NotBlank(message = "Необходим заголовок задачи")
    private String header;

    @NotNull
    private TaskStatus status;

    @NotNull
    private TaskPriority priority;

    @NotNull
    private Long authorId;

    @NotNull
    private Long executorId;

    @NotBlank(message = "Необходимо описание задачи")
    private String description;

    public void authorIdToDto(TaskDTO taskDTO, Long authorId) {

        taskDTO.authorId = authorId;

    }

    public TaskDTO() {
    }

    public TaskDTO(Long id, String header, TaskStatus status, TaskPriority priority, Long authorId, Long executorId, String description) {
        this.id = id;
        this.header = header;
        this.status = status;
        this.priority = priority;
        this.authorId = authorId;
        this.executorId = executorId;
        this.description = description;
    }

}
