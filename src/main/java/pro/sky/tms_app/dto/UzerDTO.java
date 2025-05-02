package pro.sky.tms_app.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import pro.sky.tms_app.entity.UzerRole;

@Data
public class UzerDTO {

    @NotNull
    private Long id;

    @NotNull
    @Pattern(regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$"
            , message = "Требуется логин: email пользователя")
    private String email;

    @Enumerated(value = EnumType.STRING)
    private UzerRole role;

    public UzerDTO() {
    }

    public UzerDTO(Long id, String email, UzerRole role) {
        this.id = id;
        this.email = email;
        this.role = role;
    }

}
