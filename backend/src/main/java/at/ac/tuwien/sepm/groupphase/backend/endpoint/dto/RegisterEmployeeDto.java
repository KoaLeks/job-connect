package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto;

import at.ac.tuwien.sepm.groupphase.backend.entity.Time;
import at.ac.tuwien.sepm.groupphase.backend.util.Gender;
import at.ac.tuwien.sepm.groupphase.backend.util.annotation.IsAdult;

import javax.validation.constraints.NotNull;
import java.rmi.registry.Registry;
import java.time.LocalDateTime;
import java.util.Set;

public class RegisterEmployeeDto extends ProfileDto{

    @NotNull(message = "Geschlecht muss angegeben sein")
    private Gender gender;

    @NotNull(message = "Geburtstag muss angegeben sein")
    @IsAdult(message = "Benutzer muss volljährig sein")
    private LocalDateTime birthDate;

    private Set<Time> times;

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

    public Set<Time> getTimes() {
        return times;
    }

    public void setTimes(Set<Time> times) {
        this.times = times;
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
            ", times=" + times + '\'' +
            '}';
    }
}
