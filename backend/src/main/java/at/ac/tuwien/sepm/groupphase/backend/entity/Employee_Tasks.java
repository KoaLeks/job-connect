package at.ac.tuwien.sepm.groupphase.backend.entity;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.SimpleTaskDto;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Employee_Tasks {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Employee employee;

    @ManyToOne
    private Task task;

    //This value initially has to be null
    @Column
    private Boolean accepted;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public Boolean getAccepted() {
        return accepted;
    }

    public void setAccepted(Boolean accepted) {
        this.accepted = accepted;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee_Tasks that = (Employee_Tasks) o;
        return Objects.equals(id, that.id) &&
            Objects.equals(employee, that.employee) &&
            Objects.equals(task.getId(), that.task.getId()) &&
            Objects.equals(accepted, that.accepted);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, employee, task, accepted);
    }

    @Override
    public String toString() {
        return "Employee_Tasks{" +
            "id=" + id +
            ", employee=" + employee +
            ", task=" + task.getId() +
            ", accepted=" + accepted +
            '}';
    }

    public static final class Employee_TasksBuilder {
        private Long id;
        private Employee employee;
        private Task task;
        //This value initially has to be null
        private Boolean accepted;

        private Employee_TasksBuilder() {
        }

        public static Employee_TasksBuilder anEmployee_Tasks() {
            return new Employee_TasksBuilder();
        }

        public Employee_TasksBuilder withId(Long id) {
            this.id = id;
            return this;
        }

        public Employee_TasksBuilder withEmployee(Employee employee) {
            this.employee = employee;
            return this;
        }

        public Employee_TasksBuilder withTask(Task task) {
            this.task = task;
            return this;
        }

        public Employee_TasksBuilder withAccepted(Boolean accepted) {
            this.accepted = accepted;
            return this;
        }

        public Employee_Tasks build() {
            Employee_Tasks employee_Tasks = new Employee_Tasks();
            employee_Tasks.setId(id);
            employee_Tasks.setEmployee(employee);
            employee_Tasks.setTask(task);
            employee_Tasks.setAccepted(accepted);
            return employee_Tasks;
        }
    }
}
