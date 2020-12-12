package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto;

import at.ac.tuwien.sepm.groupphase.backend.entity.Task;
import at.ac.tuwien.sepm.groupphase.backend.entity.Time;
import at.ac.tuwien.sepm.groupphase.backend.util.Gender;
import at.ac.tuwien.sepm.groupphase.backend.util.annotation.IsAdult;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;

public class EditEmployeeDto {

    private Long id;

    @Valid
    @NotNull(message = "Profil details dürfen nicht NULL sein")
    private EditProfileDto editProfileDto;

    private Set<Task> tasks;

    private Set<InterestDto> interestDtos;

    @NotNull(message = "Geschlecht darf nicht NULL sein")
    private Gender gender;

    @NotNull(message = "Geburtstag darf nicht NULL sein")
    @IsAdult(message = "Benutzer müssen volljährig sein")
    private LocalDateTime birthDate;

    @Valid
    private Set<TimeDto> times;

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

    public LocalDateTime getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDateTime birthDate) {
        this.birthDate = birthDate;
    }

    public Set<TimeDto> getTimes() {
        return times;
    }

    public void setTimes(Set<TimeDto> times) {
        this.times = times;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EditEmployeeDto)) return false;
        EditEmployeeDto that = (EditEmployeeDto) o;
        return Objects.equals(id, that.id) &&
            Objects.equals(editProfileDto, that.editProfileDto) &&
            Objects.equals(tasks, that.tasks) &&
            Objects.equals(interestDtos, that.interestDtos) &&
            gender == that.gender &&
            Objects.equals(birthDate, that.birthDate) &&
            Objects.equals(times, that.times);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, editProfileDto, tasks, interestDtos, gender, birthDate, times);
    }

    @Override
    public String toString() {
        return "EmployeeDto{" +
            "id=" + id +
            ", editProfileDto=" + editProfileDto +
            ", tasks=" + tasks +
            ", interestDtos=" + interestDtos +
            ", gender=" + gender +
            ", birthDate=" + birthDate +
            ", times=" + times.size() +
            '}';
    }

    public static final class EditEmployeeDtoBuilder{
        private Long id;
        private EditProfileDto editProfileDto;
        private Set<Task> tasks;
        private Set<InterestDto> interestDtos;
        private Gender gender;
        private LocalDateTime birthDate;
        private Set<TimeDto> times;

        private EditEmployeeDtoBuilder(){}

        public static EditEmployeeDto.EditEmployeeDtoBuilder aEmployeeDto(){
            return new EditEmployeeDto.EditEmployeeDtoBuilder();
        }
        public EditEmployeeDto.EditEmployeeDtoBuilder withEditProfileDto(EditProfileDto editProfileDto){
            this.editProfileDto = editProfileDto;
            return this;
        }
        public EditEmployeeDto.EditEmployeeDtoBuilder withTasks(Set<Task> tasks){
            this.tasks = tasks;
            return this;
        }
        public EditEmployeeDto.EditEmployeeDtoBuilder withInterestDtos(Set<InterestDto> interestDtos){
            this.interestDtos = interestDtos;
            return this;
        }
        public EditEmployeeDto.EditEmployeeDtoBuilder withGender(Gender gender){
            this.gender = gender;
            return this;
        }
        public EditEmployeeDto.EditEmployeeDtoBuilder withBirthDate(LocalDateTime birthDate){
            this.birthDate = birthDate;
            return this;
        }
        public EditEmployeeDto.EditEmployeeDtoBuilder withTimes(Set<TimeDto> times){
            this.times = times;
            return this;
        }

        public EditEmployeeDto build(){
            EditEmployeeDto editEmployeeDto = new EditEmployeeDto();
            editEmployeeDto.setId(id);
            editEmployeeDto.setEditProfileDto(editProfileDto);
            editEmployeeDto.setInterestDtos(interestDtos);
            editEmployeeDto.setTasks(tasks);
            editEmployeeDto.setGender(gender);
            editEmployeeDto.setBirthDate(birthDate);
            editEmployeeDto.setTimes(times);
            return editEmployeeDto;
        }
    }
}
