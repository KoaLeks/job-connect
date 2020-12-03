package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto;

import at.ac.tuwien.sepm.groupphase.backend.entity.Task;
import at.ac.tuwien.sepm.groupphase.backend.util.Gender;

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

    private Set<InterestDto> interestDtos;

    private Gender gender;

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

    public Set<InterestDto> getInterestDtos() {
        return interestDtos;
    }

    public void setInterestDtos(Set<InterestDto> interestDtos) {
        this.interestDtos = interestDtos;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
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
            ", interestDtos=" + interestDtos +
            ", gender=" + gender +
            '}';
    }

    public static final class EmployeeDtoBuilder{
        private Long id;
        private ProfileDto profileDto;
        private Set<Task> tasks;
        private Set<InterestDto> interestDtos;
        private Gender gender;

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
        public EmployeeDto.EmployeeDtoBuilder withInterestDtos(Set<InterestDto> interestDtos){
            this.interestDtos = interestDtos;
            return this;
        }
        public EmployeeDto.EmployeeDtoBuilder withGender(Gender gender){
            this.gender = gender;
            return this;
        }
        public EmployeeDto build(){
            EmployeeDto employeeDto = new EmployeeDto();
            employeeDto.setId(id);
            employeeDto.setProfileDto(profileDto);
            employeeDto.setInterestDtos(interestDtos);
            employeeDto.setTasks(tasks);
            employeeDto.setGender(gender);
            return employeeDto;
        }
    }
}
