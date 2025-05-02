package pro.sky.tms_app.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CommDTO {

    @NotNull
    private Long id;

    @NotNull
    private Long taskId;

    @NotBlank(message = "Необходим текст комментария к задаче")
    private String description;

    public CommDTO taskIdToDto(CommDTO commDTO, Long taskId) {

        commDTO.taskId = taskId;
        return commDTO;

    }

}
