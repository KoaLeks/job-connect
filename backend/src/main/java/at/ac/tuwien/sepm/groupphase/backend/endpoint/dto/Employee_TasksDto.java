package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto;

public class Employee_TasksDto {
    Long id;
    Long employeeId;
    Long taskId;
    Boolean accepted;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public Boolean getAccepted() {
        return accepted;
    }

    public void setAccepted(Boolean accepted) {
        this.accepted = accepted;
    }

    public static final class Employee_TasksDtoBuilder {
        Long id;
        Long employeeId;
        Long taskId;
        Boolean accept;

        private Employee_TasksDtoBuilder() {
        }

        public static Employee_TasksDtoBuilder anEmployee_TasksDto() {
            return new Employee_TasksDtoBuilder();
        }

        public Employee_TasksDtoBuilder withId(Long id) {
            this.id = id;
            return this;
        }

        public Employee_TasksDtoBuilder withEmployee(Long employee) {
            this.employeeId = employee;
            return this;
        }

        public Employee_TasksDtoBuilder withTask(Long task) {
            this.taskId = task;
            return this;
        }

        public Employee_TasksDtoBuilder withAccept(Boolean accept) {
            this.accept = accept;
            return this;
        }

        public Employee_TasksDto build() {
            Employee_TasksDto employee_TasksDto = new Employee_TasksDto();
            employee_TasksDto.setId(id);
            employee_TasksDto.setEmployeeId(employeeId);
            employee_TasksDto.setTaskId(taskId);
            employee_TasksDto.setAccepted(accept);
            return employee_TasksDto;
        }
    }
}
