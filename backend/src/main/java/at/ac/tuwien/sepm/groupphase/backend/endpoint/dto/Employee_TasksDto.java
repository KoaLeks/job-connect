package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto;

public class Employee_TasksDto {
    Long id;
    SuperSimpleEmployeeDto employee;
    Long taskId;
    Boolean accepted;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SuperSimpleEmployeeDto getEmployee() {
        return employee;
    }

    public void setEmployee(SuperSimpleEmployeeDto employee) {
        this.employee = employee;
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
        SuperSimpleEmployeeDto employee;
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

        public Employee_TasksDtoBuilder withEmployee(SuperSimpleEmployeeDto employee) {
            this.employee = employee;
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
            employee_TasksDto.setEmployee(employee);
            employee_TasksDto.setTaskId(taskId);
            employee_TasksDto.setAccepted(accept);
            return employee_TasksDto;
        }
    }
}
