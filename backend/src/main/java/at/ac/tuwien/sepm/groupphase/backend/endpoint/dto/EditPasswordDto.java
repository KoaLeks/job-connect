package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class EditPasswordDto {

    @NotNull(message = "Passwort muss angegeben sein")
    @NotBlank(message = "Passwort darf nicht leer sein")
    @Size(max = 255)
    private String currentPassword;

    @NotNull(message = "Neues Passwort muss angegeben sein")
    @NotBlank(message = "Neues Passwort darf nicht leer sein")
    @Size(max = 255)
    private String newPassword;

    public String getCurrentPassword() {
        return currentPassword;
    }

    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    @Override
    public String toString() {
        return "EditPasswordDto{" +
            "password='" + currentPassword + '\'' +
            ", newPassword='" + newPassword + '\'' +
            '}';
    }

    public static final class EditPasswordDtoBuilder{
        private String currentPassword;
        private String newPassword;

        private EditPasswordDtoBuilder(){}

        public static EditPasswordDtoBuilder aEditPasswordDto(){
            return new EditPasswordDtoBuilder();
        }

        public EditPasswordDtoBuilder withCurrentPassword(String currentPassword){
            this.currentPassword = currentPassword;
            return this;
        }

        public EditPasswordDtoBuilder withNewPassword(String newPassword){
            this.newPassword = newPassword;
            return this;
        }

        public EditPasswordDto build(){
            EditPasswordDto editPasswordDto = new EditPasswordDto();
            editPasswordDto.setNewPassword(this.newPassword);
            editPasswordDto.setCurrentPassword(this.currentPassword);
            return editPasswordDto;
        }
    }
}
