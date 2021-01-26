package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

public class ProfileDto {

    private Long id;

    @NotNull(message = "Nachname muss angegeben sein")
    @NotBlank(message = "Nachname darf nicht leer sein")
    @Size(max = 100)
    private String lastName;

    @NotNull(message = "Vorname muss angegeben sein")
    @NotBlank(message = "Vorname darf nicht leer sein")
    @Size(max = 100)
    private String firstName;

    @NotNull(message = "E-Mail dmuss angegeben sein")
    @Email(message = "E-Mail muss gültig sein")
    @Size(max = 100)
    private String email;

    @NotNull(message = "Passwort muss angegeben sein")
    @NotBlank(message = "Passwort darf nicht leer sein")
    @Size(max = 255)
    private String password;

    @Size(max = 10000)
    private String publicInfo;

    private Byte[] picture;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public Byte[] getPicture() {
        return picture;
    }

    public void setPicture(Byte[] image) {
        this.picture = image;
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
        return Objects.hash(id, lastName, firstName, email, password, publicInfo);
    }

    @Override
    public String toString() {
        return "ProfileDto{" +
            "id='" + id + '\'' +
            "lastName='" + lastName + '\'' +
            ", firstName='" + firstName + '\'' +
            ", email='" + email + '\'' +
            ", password='" + password + '\'' +
            ", publicInfo='" + publicInfo + '\'' +
            ", picture=" + picture +
            '}';
    }

    public static final class ProfileDtoBuilder{
        private Long id;
        private String lastName;
        private String firstName;
        private String email;
        private String password;
        private String publicInfo;
        private Byte[] picture;

        private ProfileDtoBuilder(){
        }

        public static ProfileDtoBuilder aProfileDto(){
            return new ProfileDtoBuilder();
        }

        public ProfileDtoBuilder withId(Long id){
            this.id = id;
            return this;
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

        public ProfileDtoBuilder isPicture(Byte[] picture) {
            this.picture = picture;
            return this;
        }

        public ProfileDto build(){
            ProfileDto profileDto = new ProfileDto();
            profileDto.setId(id);
            profileDto.setEmail(email);
            profileDto.setPassword(password);
            profileDto.setFirstName(firstName);
            profileDto.setLastName(lastName);
            profileDto.setPublicInfo(publicInfo);
            profileDto.setPicture(picture);
            return profileDto;
        }
    }
}
