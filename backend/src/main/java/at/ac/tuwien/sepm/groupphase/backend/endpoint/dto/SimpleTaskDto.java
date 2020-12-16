package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto;

import java.util.Set;

public class SimpleTaskDto {

    private Long id;
    private Long eventId;
    private String description;
    private Integer employeeCount;
    private Double paymentHourly;
    private Set<Employee_TasksDto> employees;
    private SimpleInterestAreaDto interestArea;

    @Override
    public String toString() {
        return "SimpleTaskDto{" +
            "id=" + id +
            ", eventId=" + eventId +
            ", description='" + description + '\'' +
            ", employeeCount=" + employeeCount +
            ", paymentHourly=" + paymentHourly +
            ", employees=" + employees +
            ", interestArea=" + interestArea +
            '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
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

    public Set<Employee_TasksDto> getEmployees() {
        return employees;
    }

    public void setEmployees(Set<Employee_TasksDto> employees) {
        this.employees = employees;
    }

    public SimpleInterestAreaDto getInterestArea() {
        return interestArea;
    }

    public void setInterestArea(SimpleInterestAreaDto interestArea) {
        this.interestArea = interestArea;
    }

    public static class SimpleTaskDtoBuilder{

        private Long id;
        private Long eventId;
        private String description;
        private Integer employeeCount;
        private Double paymentHourly;
        private Set<Employee_TasksDto> employees;
        private SimpleInterestAreaDto interestArea;

        private SimpleTaskDtoBuilder() {
        }

        public static SimpleTaskDtoBuilder aTask() {
            return new SimpleTaskDtoBuilder();
        }

        public SimpleTaskDtoBuilder withId(Long id) {
            this.id = id;
            return this;
        }

        public SimpleTaskDtoBuilder withEventId(Long eventId) {
            this.eventId = eventId;
            return this;
        }

        public SimpleTaskDtoBuilder withDescription(String description) {
            this.description = description;
            return this;
        }

        public SimpleTaskDtoBuilder withEmployeeCount(Integer employeeCount) {
            this.employeeCount = employeeCount;
            return this;
        }

        public SimpleTaskDtoBuilder withPaymentHourly(Double paymentHourly) {
            this.paymentHourly = paymentHourly;
            return this;
        }

        public SimpleTaskDtoBuilder withEmployees(Set<Employee_TasksDto> employees) {
            this.employees = employees;
            return this;
        }

        public SimpleTaskDtoBuilder withInterestArea(SimpleInterestAreaDto interestArea) {
            this.interestArea = interestArea;
            return this;
        }

        public SimpleTaskDto build() {
            SimpleTaskDto simpleTaskDto = new SimpleTaskDto();
            simpleTaskDto.setId(id);
            simpleTaskDto.setEventId(eventId);
            simpleTaskDto.setDescription(description);
            simpleTaskDto.setEmployeeCount(employeeCount);
            simpleTaskDto.setPaymentHourly(paymentHourly);
            simpleTaskDto.setEmployees(employees);
            simpleTaskDto.setInterestArea(interestArea);
            return simpleTaskDto;
        }
    }
}
