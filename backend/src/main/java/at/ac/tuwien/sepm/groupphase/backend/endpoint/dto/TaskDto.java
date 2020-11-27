package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto;

import at.ac.tuwien.sepm.groupphase.backend.entity.*;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;

public class TaskDto {

    private Long id;
    private String description;
    private Integer employeeCount;
    private Double paymentHourly;
    private Event event;
    private Set<Employee> employees;
    private InterestArea interestArea;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getEmployeeCount() {
        return employeeCount;
    }

    public void setEmployeeCount(Integer employeeCount) {
        this.employeeCount = employeeCount;
    }

    public Double getPaymentHourly() {
        return paymentHourly;
    }

    public void setPaymentHourly(Double paymentHourly) {
        this.paymentHourly = paymentHourly;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public Set<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(Set<Employee> employees) {
        this.employees = employees;
    }

    public InterestArea getInterestArea() {
        return interestArea;
    }

    public void setInterestArea(InterestArea interestArea) {
        this.interestArea = interestArea;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TaskDto taskDto = (TaskDto) o;
        return Objects.equals(id, taskDto.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "TaskDto{" +
            "id=" + id +
            ", description='" + description + '\'' +
            ", employeeCount=" + employeeCount +
            ", paymentHourly=" + paymentHourly +
            ", event=" + event +
            ", employees=" + employees +
            ", interestArea=" + interestArea +
            '}';
    }

    public static final class TaskDtoBuilder {
        private Long id;
        private String description;
        private Integer employeeCount;
        private Double paymentHourly;
        private Event event;
        private Set<Employee> employees;
        private InterestArea interestArea;

        private TaskDtoBuilder() {
        }

        public static TaskDtoBuilder aTask() {
            return new TaskDtoBuilder();
        }

        public TaskDtoBuilder withId(Long id) {
            this.id = id;
            return this;
        }

        public TaskDtoBuilder withDescription(String description) {
            this.description = description;
            return this;
        }

        public TaskDtoBuilder withEmployeeCount(Integer employeeCount) {
            this.employeeCount = employeeCount;
            return this;
        }

        public TaskDtoBuilder withPaymentHourly(Double paymentHourly) {
            this.paymentHourly = paymentHourly;
            return this;
        }

        public TaskDtoBuilder withEvent(Event event) {
            this.event = event;
            return this;
        }

        public TaskDtoBuilder withEmployees(Set<Employee> employees) {
            this.employees = employees;
            return this;
        }

        public TaskDtoBuilder withInterestArea(InterestArea interestArea) {
            this.interestArea = interestArea;
            return this;
        }

        public TaskDto build() {
            TaskDto task = new TaskDto();
            task.setDescription(description);
            task.setId(id);
            task.setEmployeeCount(employeeCount);
            task.setEmployees(employees);
            task.setEvent(event);
            task.setInterestArea(interestArea);
            task.setPaymentHourly(paymentHourly);
            return task;
        }
    }


}


