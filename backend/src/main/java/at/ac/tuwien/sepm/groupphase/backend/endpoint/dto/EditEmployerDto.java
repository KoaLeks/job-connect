package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto;

import at.ac.tuwien.sepm.groupphase.backend.entity.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;
import java.util.Set;

public class EditEmployerDto {

    private Long id;

    @Valid
    @NotNull(message = "Profil details dürfen nicht NULL sein")
    private EditProfileDto editProfileDto;

    @NotNull(message = "Firmenname darf nicht NULL sein")
    @Size(max = 255)
    private String companyName;

    @Size(max = 1000)
    private String description;

    private Set<Event> events;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EditProfileDto getEditProfileDto() {
        return editProfileDto;
    }

    public void setEditProfileDto(EditProfileDto editProfileDto) {
        this.editProfileDto = editProfileDto;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Event> getEvents() {
        return events;
    }

    public void setEvents(Set<Event> events) {
        this.events = events;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EditEmployerDto)) return false;
        EditEmployerDto that = (EditEmployerDto) o;
        return Objects.equals(id, that.id) &&
            Objects.equals(editProfileDto, that.editProfileDto) &&
            Objects.equals(companyName, that.companyName) &&
            Objects.equals(description, that.description) &&
            Objects.equals(events, that.events);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, editProfileDto, companyName, description, events);
    }

    @Override
    public String toString() {
        return "EditEmployerDto{" +
            "id=" + id +
            ", editProfileDto=" + editProfileDto +
            ", companyName='" + companyName + '\'' +
            ", description='" + description + '\'' +
            ", events=" + events +
            '}';
    }

    public static final class EditEmployerDtoBuilder {
        private Long id;
        private EditProfileDto editProfileDto;
        private String companyName;
        private String description;
        private Set<Event> events;

        private EditEmployerDtoBuilder(){}

        public static EditEmployerDto.EditEmployerDtoBuilder aEmployerDto(){
            return new EditEmployerDto.EditEmployerDtoBuilder();
        }
        public EditEmployerDto.EditEmployerDtoBuilder withProfileDto(EditProfileDto editProfileDto){
            this.editProfileDto = editProfileDto;
            return this;
        }
        public EditEmployerDto.EditEmployerDtoBuilder withCompanyName(String companyName){
            this.companyName = companyName;
            return this;
        }
        public EditEmployerDto.EditEmployerDtoBuilder withDescription(String description){
            this.description = description;
            return this;
        }
        public EditEmployerDto.EditEmployerDtoBuilder withEvents(Set<Event> events){
            this.events = events;
            return this;
        }

        public EditEmployerDto build(){
            EditEmployerDto editEmployerDto = new EditEmployerDto();
            editEmployerDto.setId(id);
            editEmployerDto.setEditProfileDto(editProfileDto);
            editEmployerDto.setCompanyName(companyName);
            editEmployerDto.setDescription(description);
            editEmployerDto.setEvents(events);
            return editEmployerDto;
        }
    }
}
