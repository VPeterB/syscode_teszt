package vpteszt.profile_service.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.UUID;

@Entity
@Getter @Setter @NoArgsConstructor
public class Student {
    private @Id UUID id;
    private @NotEmpty String name;
    private @Email String email;

    public Student(String name, String email){
        this.id = UUID.randomUUID();
        this.name = name;
        this.email = email;
    }
}
