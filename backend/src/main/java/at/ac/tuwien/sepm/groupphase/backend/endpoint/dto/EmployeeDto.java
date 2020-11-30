package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto;


import at.ac.tuwien.sepm.groupphase.backend.entity.Interest;
import at.ac.tuwien.sepm.groupphase.backend.entity.Task;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Objects;
import java.util.Set;

public class EmployeeDto {

    private Long id;

    @Valid
    @NotNull(message = "Profile details must not be null")
    private ProfileDto profileDto;

    private Set<Task> tasks;

    private Set<Interest> interests;

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

    public Set<Task> getTasks() {
        return tasks;
    }

    public void setTasks(Set<Task> tasks) {
        this.tasks = tasks;
    }

    public Set<Interest> getInterests() {
        return interests;
    }

    public void setInterests(Set<Interest> interests) {
        this.interests = interests;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmployeeDto employee = (EmployeeDto) o;
        return Objects.equals(id, employee.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "EmployeeDto{" +
            "id=" + id +
            ", profileDto=" + profileDto +
            ", tasks=" + tasks +
            ", interests=" + interests +
            '}';
    }

    public static final class EmployeeDtoBuilder{
        private Long id;
        private ProfileDto profileDto;
        private Set<Task> tasks;
        private Set<Interest> interests;

        private EmployeeDtoBuilder(){}

        public static EmployeeDto.EmployeeDtoBuilder aEmployeeDto(){
            return new EmployeeDto.EmployeeDtoBuilder();
        }
        public EmployeeDto.EmployeeDtoBuilder withProfileDto(ProfileDto profileDto){
            this.profileDto = profileDto;
            return this;
        }
        public EmployeeDto.EmployeeDtoBuilder withTasks(Set<Task> tasks){
            this.tasks = tasks;
            return this;
        }
        public EmployeeDto.EmployeeDtoBuilder withInterests(Set<Interest> interests){
            this.interests = interests;
            return this;
        }
        public EmployeeDto build(){
            EmployeeDto employeeDto = new EmployeeDto();
            employeeDto.setId(id);
            employeeDto.setProfileDto(profileDto);
            employeeDto.setInterests(interests);
            employeeDto.setTasks(tasks);
            return employeeDto;
        }
    }
}
