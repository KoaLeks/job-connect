package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Objects;

public class ApplicationStatusDto {

    @NotNull
    private Long task;
    @NotNull
    private Long employee;
    @NotNull
    private boolean accepted;

    public Long getTask() {
        return task;
    }

    public void setTask(Long task) {
        this.task = task;
    }

    public Long getEmployee() {
        return employee;
    }

    public void setEmployee(Long employee) {
        this.employee = employee;
    }

    public boolean isAccepted() {
        return accepted;
    }

    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ApplicationStatusDto that = (ApplicationStatusDto) o;
        return accepted == that.accepted &&
            Objects.equals(task, that.task) &&
            Objects.equals(employee, that.employee);
    }

    @Override
    public int hashCode() {
        return Objects.hash(task, employee, accepted);
    }

    @Override
    public String toString() {
        return "ApplicationStatusDto{" +
            "task=" + task +
            ", employee=" + employee +
            ", accepted=" + accepted +
            '}';
    }
}
