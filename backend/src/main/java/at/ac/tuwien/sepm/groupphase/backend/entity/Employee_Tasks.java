package at.ac.tuwien.sepm.groupphase.backend.entity;

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
            Objects.equals(task, that.task) &&
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
            ", task=" + task +
            ", accepted=" + accepted +
            '}';
    }


}
