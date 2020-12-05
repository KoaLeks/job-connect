package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class EditPasswordDto {
    @NotNull(message = "E-Mail darf nicht NULL sein")
    @Size(max = 100)
    @Email(message = "E-Mail muss gültig sein")
    private String email;

    @NotNull(message = "Passwort darf nicht NULL sein")
    @NotBlank(message = "Passwort darf nicht leer sein")
    @Size(max = 255)
    private String currentPassword;

    @NotNull(message = "Neues Passwort darf nicht NULL sein")
    @NotBlank(message = "Neues Passwort darf nicht leer sein")
    @Size(max = 255)
    private String newPassword;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

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
            "email='" + email + '\'' +
            ", password='" + currentPassword + '\'' +
            ", newPassword='" + newPassword + '\'' +
            '}';
    }
}
