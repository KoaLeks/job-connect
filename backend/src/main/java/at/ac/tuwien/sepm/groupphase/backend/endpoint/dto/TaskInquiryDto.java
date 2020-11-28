package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto;

import at.ac.tuwien.sepm.groupphase.backend.entity.*;

import javax.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;

public class TaskInquiryDto {

    @NotNull(message = "must not be null")
    @NotBlank(message = "must not be empty")
    @Size(max = 1000)
    private String description;

    @NotNull(message = "must not be null")
    private Integer employeeCount;

    @NotNull(message = "must not be null")
    @PositiveOrZero
    private Double paymentHourly;

    @NotNull(message = "must not be null")
    private Set<Employee> employees;

    @NotNull(message = "must not be null")
    private InterestArea interestArea;

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
        TaskInquiryDto that = (TaskInquiryDto) o;
        return Objects.equals(description, that.description) &&
            Objects.equals(employeeCount, that.employeeCount) &&
            Objects.equals(paymentHourly, that.paymentHourly) &&
            Objects.equals(employees, that.employees) &&
            Objects.equals(interestArea, that.interestArea);
    }

    @Override
    public int hashCode() {
        return Objects.hash(description, employeeCount, paymentHourly, employees, interestArea);
    }

    @Override
    public String toString() {
        return "TaskDto{" +
            "description='" + description + '\'' +
            ", employeeCount=" + employeeCount +
            ", paymentHourly=" + paymentHourly +
            ", employees=" + employees +
            ", interestArea=" + interestArea +
            '}';
    }

    public static final class TaskInquiryDtoBuilder {
        private String description;
        private Integer employeeCount;
        private Double paymentHourly;
        private Set<Employee> employees;
        private InterestArea interestArea;

        private TaskInquiryDtoBuilder() {
        }

        public static TaskInquiryDtoBuilder aTask() {
            return new TaskInquiryDtoBuilder();
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
            task.setDescription(description);
            task.setEmployeeCount(employeeCount);
            task.setEmployees(employees);
            task.setInterestArea(interestArea);
            task.setPaymentHourly(paymentHourly);
            return task;
        }
    }


}


