package vpteszt.profile_service.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@NoArgsConstructor
public class StudentCreateDTO {
    private @NotEmpty String name;
    private @Email String email;
}
