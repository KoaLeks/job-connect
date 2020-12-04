package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto;

import at.ac.tuwien.sepm.groupphase.backend.util.Gender;
import at.ac.tuwien.sepm.groupphase.backend.util.annotation.IsAdult;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class RegisterEmployeeDto extends ProfileDto{

    @NotNull(message = "Gender must not be null")
    private Gender gender;

    @NotNull(message = "Birth date must not be null")
    @IsAdult(message = "User must be an adult")
    private LocalDateTime birthDate;

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public LocalDateTime getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDateTime birthDate) {
        this.birthDate = birthDate;
    }

    @Override
    public String toString() {
        return "RegisterEmployeeDto{" +
            "lastName='" + this.getLastName() + '\'' +
            ", firstName='" + this.getFirstName() + '\'' +
            ", email='" + this.getLastName() + '\'' +
            ", password='" + this.getPassword() + '\'' +
            ", publicInfo='" + this.getPublicInfo() + '\'' +
            ", gender='" + gender + '\'' +
            ", birthDate='" + birthDate + '\'' +
            '}';
    }
}
