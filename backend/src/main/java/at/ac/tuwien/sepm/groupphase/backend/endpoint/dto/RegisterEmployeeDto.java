package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto;

import at.ac.tuwien.sepm.groupphase.backend.util.Gender;
import at.ac.tuwien.sepm.groupphase.backend.util.annotation.IsAdult;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class RegisterEmployeeDto extends ProfileDto{

    @NotNull(message = "Geschlecht darf nicht NULL sein")
    private Gender gender;

    @NotNull(message = "Geburtstag darf nicht NULL sein")
    @IsAdult(message = "Benutzer muss volljährig sein")
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
