package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto;

import at.ac.tuwien.sepm.groupphase.backend.entity.*;

import java.util.Objects;
import java.util.Set;

public class EmployerDto {

    private Long id;

    private ProfileDto profileDto;

    private String companyName;

    private String description;

    private Set<Event> events;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ProfileDto getProfileDto() {
        return profileDto;
    }

    public void setProfileDto(ProfileDto profileDto) {
        this.profileDto = profileDto;
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
        if (!(o instanceof EmployerDto)) return false;
        EmployerDto that = (EmployerDto) o;
        return Objects.equals(id, that.id) &&
            Objects.equals(profileDto, that.profileDto) &&
            Objects.equals(companyName, that.companyName) &&
            Objects.equals(description, that.description) &&
            Objects.equals(events, that.events);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, profileDto, companyName, description, events);
    }

    @Override
    public String toString() {
        return "EmployerDto{" +
            "id=" + id +
            ", profileDto=" + profileDto +
            ", companyName='" + companyName + '\'' +
            ", description='" + description + '\'' +
            ", events=" + events +
            '}';
    }

    public static final class EmployerDtoBuilder {
        private Long id;
        private ProfileDto profileDto;
        private String companyName;
        private String description;
        private Set<Event> events;

        private EmployerDtoBuilder(){}

        public static EmployerDto.EmployerDtoBuilder aEmployerDto(){
            return new EmployerDto.EmployerDtoBuilder();
        }
        public EmployerDto.EmployerDtoBuilder withProfileDto(ProfileDto profileDto){
            //this.id = profileDto.getId();
            this.profileDto = profileDto;
            return this;
        }
        public EmployerDto.EmployerDtoBuilder withCompanyName(String companyName){
            this.companyName = companyName;
            return this;
        }
        public EmployerDto.EmployerDtoBuilder withDescription(String description){
            this.description = description;
            return this;
        }
        public EmployerDto.EmployerDtoBuilder withEvents(Set<Event> events){
            this.events = events;
            return this;
        }

        public EmployerDto build(){
            EmployerDto employerDto = new EmployerDto();
            employerDto.setId(id);
            employerDto.setProfileDto(profileDto);
            employerDto.setCompanyName(companyName);
            employerDto.setDescription(description);
            employerDto.setEvents(events);
            return employerDto;
        }
    }
}
