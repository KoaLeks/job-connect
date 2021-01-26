package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class RegisterEmployerDto extends ProfileDto{

    @NotNull(message = "Firmenname muss angegeben sein")
    @NotBlank(message = "Firmenname darf nicht leer sein")
    @Size(max = 255)
    private String companyName;

    @Size(max = 1000)
    private String companyDescription;

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyDescription() {
        return companyDescription;
    }

    public void setCompanyDescription(String companyDescription) {
        this.companyDescription = companyDescription;
    }

}
