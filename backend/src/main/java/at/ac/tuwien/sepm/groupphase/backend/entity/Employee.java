package at.ac.tuwien.sepm.groupphase.backend.entity;

import at.ac.tuwien.sepm.groupphase.backend.util.Gender;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
public class Employee {
    @Id
    private Long id;
    @OneToOne
    @MapsId
    private Profile profile;
    @ManyToMany
    private Set<Task> tasks;
    @ManyToMany
    private Set<Interest> interests;
    @Enumerated(EnumType.STRING)
    private Gender gender;

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
        Employee employee = (Employee) o;
        return Objects.equals(id, employee.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Employee{" +
            "id=" + id +
            ", profile=" + profile +
            ", tasks=" + tasks +
            ", interests=" + interests +
            ", gender=" + gender +
            '}';
    }

    public static final class EmployeeBuilder {
        private Long id;
        private Profile profile;
        private Set<Task> tasks;
        private Set<Interest> interests;
        private Gender gender;


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

        public EmployeeBuilder withTasks(Set<Task> tasks) {
            this.tasks = tasks;
            return this;
        }

        public EmployeeBuilder withInterests(Set<Interest> interests) {
            this.interests = interests;
            return this;
        }
        public EmployeeBuilder withGender(Gender gender){
            this.gender = gender;
            return this;
        }

        public Employee build() {
            Employee employee = new Employee();
            employee.setId(id);
            employee.setProfile(profile);
            employee.setInterests(interests);
            employee.setTasks(tasks);
            employee.setGender(gender);
            return employee;
        }
    }
}
