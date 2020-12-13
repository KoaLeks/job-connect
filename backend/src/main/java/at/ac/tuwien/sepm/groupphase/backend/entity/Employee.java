package at.ac.tuwien.sepm.groupphase.backend.entity;

import at.ac.tuwien.sepm.groupphase.backend.util.Gender;
import at.ac.tuwien.sepm.groupphase.backend.util.annotation.IsAdult;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;

@Entity
public class Employee {
    @Id
    private Long id;

    @OneToOne
    @MapsId
    private Profile profile;


    @OneToMany(mappedBy = "employee")
    private Set<Employee_Tasks> tasks;

    @OneToMany(mappedBy = "employee")
    private Set<Interest> interests;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(nullable = false)
    @NotNull
    @IsAdult
    private LocalDateTime birthDate;

    public Set<Employee_Tasks> getTasks() {
        return tasks;
    }

    public void setTasks(Set<Employee_Tasks> tasks) {
        this.tasks = tasks;
    }

    @OneToMany(mappedBy = "employee")
    private Set<Time> times;

    public Set<Time> getTimes() {
        return times;
    }

    public void setTimes(Set<Time> times) {
        this.times = times;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }


    public Set<Interest> getInterests() {
        return interests;
    }

    public void setInterests(Set<Interest> interests) {
        this.interests = interests;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return Objects.equals(id, employee.id) &&
            Objects.equals(profile, employee.profile) &&
            Objects.equals(gender, employee.gender) &&
            Objects.equals(birthDate, employee.birthDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, profile, gender, birthDate);
    }

    @Override
    public String toString() {
        return "Employee{" +
            "id=" + id +
            ", profile=" + profile +
            ", tasks=" + tasks +
            ", interests=" + (interests == null ? "null":interests.size()) +
            ", gender=" + gender +
            ", birthdate=" + birthDate +
            ", times=" + (times == null ? "null":times.size()) +
            ", birthDate=" + birthDate +
            '}';
    }

    public static final class EmployeeBuilder {
        private Long id;
        private Profile profile;
        private Set<Employee_Tasks> tasks;
        private Set<Interest> interests;
        private Gender gender;
        private LocalDateTime birthDate;
        private Set<Time> times;


        private EmployeeBuilder() {
        }

        public static EmployeeBuilder aEmployee() {
            return new EmployeeBuilder();
        }

        public EmployeeBuilder withProfile(Profile profile) {
            this.id = profile.getId();
            this.profile = profile;
            return this;
        }

        public EmployeeBuilder withTasks(Set<Employee_Tasks> tasks) {
            this.tasks = tasks;
            return this;
        }

        public EmployeeBuilder withInterests(Set<Interest> interests) {
            this.interests = interests;
            return this;
        }

        public EmployeeBuilder withGender(Gender gender) {
            this.gender = gender;
            return this;
        }

        public EmployeeBuilder withBirthDate(LocalDateTime birthDate) {
            this.birthDate = birthDate;
            return this;
        }

        public EmployeeBuilder withTimes(Set<Time> times) {
            this.times = times;
            return this;
        }

        public Employee build() {
            Employee employee = new Employee();
            employee.setId(id);
            employee.setProfile(profile);
            employee.setInterests(interests);
            employee.setTasks(tasks);
            employee.setGender(gender);
            employee.setBirthDate(birthDate);
            employee.setTimes(times);
            return employee;
        }
    }
}
