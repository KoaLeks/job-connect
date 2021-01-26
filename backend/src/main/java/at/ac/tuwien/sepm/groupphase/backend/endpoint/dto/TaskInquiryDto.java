package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto;

import at.ac.tuwien.sepm.groupphase.backend.entity.*;

import javax.validation.constraints.*;
import java.util.Objects;
import java.util.Set;

public class TaskInquiryDto {

    private Long id;

    @NotNull(message = "Beschreibung muss angegeben sein")
    @NotBlank(message = "Beschreibung darf nicht leer sein")
    @Size(max = 1000)
    private String description;

    @NotNull(message = "Arbeitnehmeranzahl muss angegeben sein")
    private Integer employeeCount;

    @NotNull(message = "Stundenlohn muss angegeben sein")
    @PositiveOrZero(message = "Stundenlohn muss positiv sein")
    private Double paymentHourly;

    private Long eventId;

    private Set<Employee_Tasks> employees;

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

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public Set<Employee_Tasks> getEmployees() {
        return employees;
    }

    public void setEmployees(Set<Employee_Tasks> employees) {
        this.employees = employees;
    }

    public InterestArea getInterestArea() {
        return interestArea;
    }

    public void setInterestArea(InterestArea interestArea) {
        this.interestArea = interestArea;
    }

    @AssertTrue(message = "Arbeitnehmeranzahl muss mindestens so groß sein wie die Anzahl der Arbeitnehmer")
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
        return Objects.equals(id, that.id) &&
            Objects.equals(description, that.description) &&
            Objects.equals(employeeCount, that.employeeCount) &&
            Objects.equals(paymentHourly, that.paymentHourly);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description, employeeCount, paymentHourly);
    }

    @Override
    public String toString() {
        return "TaskInquiryDto{" +
            "id=" + id +
            ", description='" + description + '\'' +
            ", employeeCount=" + employeeCount +
            ", paymentHourly=" + paymentHourly +
            ", eventId=" + eventId +
            ", employees=" + employees +
            ", interestArea=" + interestArea +
            '}';
    }

    public static final class TaskInquiryDtoBuilder {
        private Long id;
        private String description;
        private Integer employeeCount;
        private Double paymentHourly;
        private Long eventId;
        private Set<Employee_Tasks> employees;
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

        public TaskInquiryDtoBuilder withEventId(Long eventId) {
            this.eventId = eventId;
            return this;
        }

        public TaskInquiryDtoBuilder withEmployees(Set<Employee_Tasks> employees) {
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
            task.setEventId(eventId);
            task.setInterestArea(interestArea);
            task.setPaymentHourly(paymentHourly);
            return task;
        }
    }


}

