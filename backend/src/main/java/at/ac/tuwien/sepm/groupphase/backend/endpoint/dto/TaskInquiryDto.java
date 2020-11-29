package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto;

import at.ac.tuwien.sepm.groupphase.backend.entity.*;

import javax.validation.constraints.*;
import java.util.Objects;
import java.util.Set;

public class TaskInquiryDto {

    private Long id;

    @NotNull(message = "must not be null")
    @NotBlank(message = "must not be empty")
    @Size(max = 1000)
    private String description;

    @NotNull(message = "must not be null")
    private Integer employeeCount;

    @NotNull(message = "must not be null")
    @PositiveOrZero
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

    @AssertTrue(message = "employeeCount must be at least as high as number of employees")
    public boolean isValidEmployeeCount() {
        if(employees != null && employeeCount != null) {
            return employeeCount >= employees.size();
        }
        if(employees == null && employeeCount != null) {
            return employeeCount >= 0;
        }
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TaskInquiryDto that = (TaskInquiryDto) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "TaskInquiryDto{" +
            "id=" + id +
            ", description='" + description + '\'' +
            ", employeeCount=" + employeeCount +
            ", paymentHourly=" + paymentHourly +
            ", event=" + event +
            ", employees=" + employees +
            ", interestArea=" + interestArea +
            '}';
    }

    public static final class TaskInquiryDtoBuilder {
        private Long id;
        private String description;
        private Integer employeeCount;
        private Double paymentHourly;
        private Event event;
        private Set<Employee> employees;
        private InterestArea interestArea;

        private TaskInquiryDtoBuilder() {
        }

        public static TaskInquiryDtoBuilder aTask() {
            return new TaskInquiryDtoBuilder();
        }

        public TaskInquiryDtoBuilder withId(Long id) {
            this.id = id;
            return this;
        }

        public TaskInquiryDtoBuilder withDescription(String description) {
            this.description = description;
            return this;
        }

        public TaskInquiryDtoBuilder withEmployeeCount(Integer employeeCount) {
            this.employeeCount = employeeCount;
            return this;
        }

        public TaskInquiryDtoBuilder withPaymentHourly(Double paymentHourly) {
            this.paymentHourly = paymentHourly;
            return this;
        }

        public TaskInquiryDtoBuilder withEvent(Event event) {
            this.event = event;
            return this;
        }

        public TaskInquiryDtoBuilder withEmployees(Set<Employee> employees) {
            this.employees = employees;
            return this;
        }

        public TaskInquiryDtoBuilder withInterestArea(InterestArea interestArea) {
            this.interestArea = interestArea;
            return this;
        }

        public TaskInquiryDto build() {
            TaskInquiryDto task = new TaskInquiryDto();
            task.setId(id);
            task.setDescription(description);
            task.setEmployeeCount(employeeCount);
            task.setEmployees(employees);
            task.setEvent(event);
            task.setInterestArea(interestArea);
            task.setPaymentHourly(paymentHourly);
            return task;
        }
    }


}

