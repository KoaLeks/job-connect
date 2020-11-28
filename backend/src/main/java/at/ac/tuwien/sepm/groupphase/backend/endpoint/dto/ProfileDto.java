package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto;

import org.springframework.context.annotation.Profile;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

public class ProfileDto {
    @NotNull(message = "Last name must not be null")
    @Size(max = 100)
    private String lastName;

    @NotNull(message = "First name must not be null")
    @Size(max = 100)
    private String firstName;

    @NotNull(message = "Email must not be null")
    @Size(max = 100)
    private String email;

    @NotNull(message = "Password must not be null")
    @Size(max = 255)
    private String password;

    @Size(max = 10000)
    private String publicInfo;

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPublicInfo() {
        return publicInfo;
    }

    public void setPublicInfo(String publicInfo) {
        this.publicInfo = publicInfo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProfileDto that = (ProfileDto) o;
        return Objects.equals(lastName, that.lastName) &&
            Objects.equals(firstName, that.firstName) &&
            Objects.equals(email, that.email) &&
            Objects.equals(password, that.password) &&
            Objects.equals(publicInfo, that.publicInfo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lastName, firstName, email, password, publicInfo);
    }

    @Override
    public String toString() {
        return "ProfileDto{" +
            "lastName='" + lastName + '\'' +
            ", firstName='" + firstName + '\'' +
            ", email='" + email + '\'' +
            ", password='" + password + '\'' +
            ", publicInfo='" + publicInfo + '\'' +
            '}';
    }

    public static final class ProfileDtoBuilder{
        private String lastName;
        private String firstName;
        private String email;
        private String password;
        private String publicInfo;
        private ProfileDtoBuilder(){
        }

        public static ProfileDtoBuilder aProfileDto(){
            return new ProfileDtoBuilder();
        }

        public ProfileDtoBuilder withLastName(String lastName){
            this.lastName = lastName;
            return this;
        }
        public ProfileDtoBuilder withFirstName(String firstName){
            this.firstName = firstName;
            return this;
        }
        public ProfileDtoBuilder withEmail(String email){
            this.email = email;
            return this;
        }
        public ProfileDtoBuilder withPassword(String password){
            this.password = password;
            return this;
        }
        public ProfileDtoBuilder withPublicInfo(String publicInfo){
            this.publicInfo = publicInfo;
            return this;
        }
        public ProfileDto build(){
            ProfileDto profileDto = new ProfileDto();
            profileDto.setEmail(email);
            profileDto.setPassword(password);
            profileDto.setFirstName(firstName);
            profileDto.setLastName(lastName);
            profileDto.setPublicInfo(publicInfo);
            return profileDto;
        }
    }
}
