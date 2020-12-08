package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto;

import at.ac.tuwien.sepm.groupphase.backend.entity.Employee;
import at.ac.tuwien.sepm.groupphase.backend.entity.Time;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Future;
import java.time.LocalDateTime;
import java.util.Objects;

public class TimeDto {

    private Long id;
    private Employee employee;
    @Future
    private LocalDateTime start;
    @Future
    private LocalDateTime end;

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

    public LocalDateTime getStart() {
        return start;
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

    @AssertTrue(message = "Start Datum muss vor dem End Datum liegen")
    public boolean isValidDate() {
        if (end != null && start != null) {
            return end.isAfter(start);
        } else {
            return false;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TimeDto time = (TimeDto) o;
        return id.equals(time.id) &&
            employee.equals(time.employee) &&
            start.equals(time.start) &&
            end.equals(time.end);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, employee, start, end);
    }

    @Override
    public String toString() {
        return "TimeDto{" +
            "id='" + id + '\'' +
            ", employee=" + employee +
            ", start=" + start +
            ", end=" + end +
            '}';
    }

    public static final class TimeDtoBuilder {
        private Long id;
        private Employee employee;
        private LocalDateTime start;
        private LocalDateTime end;

        private TimeDtoBuilder() {
        }

        public static TimeDtoBuilder aTimeDto() {
            return new TimeDtoBuilder();
        }

        public TimeDtoBuilder withId(Long id) {
            this.id = id;
            return this;
        }

        public TimeDtoBuilder withEmployee(Employee employee) {
            this.employee = employee;
            return this;
        }

        public TimeDtoBuilder withStart(LocalDateTime start) {
            this.start = start;
            return this;
        }

        public TimeDtoBuilder withEnd(LocalDateTime end) {
            this.end = end;
            return this;
        }


        public TimeDto build() {
            TimeDto time = new TimeDto();
            time.setId(id);
            time.setEmployee(employee);
            time.setStart(start);
            time.setEnd(end);
            return time;
        }
    }


}
