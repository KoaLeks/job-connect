package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto;

import at.ac.tuwien.sepm.groupphase.backend.util.Gender;

import javax.validation.constraints.NotNull;

public class RegisterEmployeeDto extends ProfileDto{
    
    @NotNull(message = "Gender must not be null")
    private Gender gender;

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
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
            '}';
    }
}
