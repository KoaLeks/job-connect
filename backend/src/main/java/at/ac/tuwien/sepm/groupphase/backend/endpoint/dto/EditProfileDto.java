package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto;

import javax.validation.constraints.*;
import java.util.Arrays;
import java.util.Objects;

public class EditProfileDto {

    private Long id;

    @NotNull(message = "Nachname darf nicht NULL sein")
    @NotBlank(message = "Nachname darf nicht leer sein")
    @Size(max = 100)
    @Pattern(regexp = "[a-zA-ZÖöÜüÄä]+([ ]|[a-zA-ZÖöÜüÄä])*", message = "Nachname muss mit einem Buchstaben beginnen und darf keine Sonderzeichen enthalten")
    private String lastName;

    @NotNull(message = "Vorname darf nicht NULL sein")
    @NotBlank(message = "Vorname darf nicht leer sein")
    @Size(max = 100)
    @Pattern(regexp = "[a-zA-ZÖöÜüÄä]+([ ]|[a-zA-ZÖöÜüÄä])*", message = "Vorname muss mit einem Buchstaben beginnen und darf keine Sonderzeichen enthalten")
    private String firstName;

    @NotNull(message = "E-Mail darf nicht NULL sein")
    @Size(max = 100)
    @Email(message = "E-Mail muss gültig sein")
    private String email;

    // Not used for updating password
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
        if (!(o instanceof EditProfileDto)) return false;
        EditProfileDto that = (EditProfileDto) o;
        return Objects.equals(id, that.id) &&
            Objects.equals(lastName, that.lastName) &&
            Objects.equals(firstName, that.firstName) &&
            Objects.equals(email, that.email) &&
            Objects.equals(publicInfo, that.publicInfo) &&
            Arrays.equals(picture, that.picture);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, lastName, firstName, email, publicInfo);
        result = 31 * result + Arrays.hashCode(picture);
        return result;
    }

    @Override
    public String toString() {
        return "EditProfileDto{" +
            "id=" + id +
            ", lastName='" + lastName + '\'' +
            ", firstName='" + firstName + '\'' +
            ", email='" + email + '\'' +
            ", publicInfo='" + publicInfo + '\'' +
            '}';
    }

    public static final class EditProfileDtoBuilder {
        private Long id;
        private String lastName;
        private String firstName;
        private String email;
        private String publicInfo;
        private Byte[] picture;

        private EditProfileDtoBuilder() {
        }

        public static EditProfileDto.EditProfileDtoBuilder aEditProfileDto() {
            return new EditProfileDto.EditProfileDtoBuilder();
        }

        public EditProfileDto.EditProfileDtoBuilder withId(Long id) {
            this.id = id;
            return this;
        }

        public EditProfileDto.EditProfileDtoBuilder withLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public EditProfileDto.EditProfileDtoBuilder withFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public EditProfileDto.EditProfileDtoBuilder withEmail(String email) {
            this.email = email;
            return this;
        }

        public EditProfileDto.EditProfileDtoBuilder withPublicInfo(String publicInfo) {
            this.publicInfo = publicInfo;
            return this;
        }

        public EditProfileDto.EditProfileDtoBuilder isPicture(Byte[] picture) {
            this.picture = picture;
            return this;
        }

        public EditProfileDto build() {
            EditProfileDto editProfileDto = new EditProfileDto();
            editProfileDto.setId(id);
            editProfileDto.setEmail(email);
            editProfileDto.setFirstName(firstName);
            editProfileDto.setLastName(lastName);
            editProfileDto.setPublicInfo(publicInfo);
            editProfileDto.setPicture(picture);
            return editProfileDto;
        }
    }
}
