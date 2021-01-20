package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto;

import javax.validation.constraints.NotNull;
import java.util.Objects;

public class ApplicationDto {

    @NotNull
    private Long task;

    private String Message;


    public Long getTask() {
        return task;
    }

    public void setTask(Long task) {
        this.task = task;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ApplicationDto that = (ApplicationDto) o;
        return Objects.equals(task, that.task) &&
            Objects.equals(Message, that.Message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(task, Message);
    }

    @Override
    public String toString() {
        return "ApplicationDto{" +
            "task=" + task +
            ", Message='" + Message + '\'' +
            '}';
    }

    public static final class ApplicationDtoBuilder{
        private Long task;
        private String message;

        public ApplicationDtoBuilder(){}

        public static ApplicationDtoBuilder aApplicationDto(){
            return new ApplicationDtoBuilder();
        }

        public ApplicationDtoBuilder withTask(Long taskId){
            this.task = taskId;
            return this;
        }

        public ApplicationDtoBuilder withMessage(String message){
            this.message = message;
            return this;
        }

        public ApplicationDto build(){
            ApplicationDto applicationDto = new ApplicationDto();
            applicationDto.setMessage(this.message);
            applicationDto.setTask(this.task);
            return applicationDto;
        }

    }
}
