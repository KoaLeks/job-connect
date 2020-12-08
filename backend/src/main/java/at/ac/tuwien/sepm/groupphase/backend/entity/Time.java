package at.ac.tuwien.sepm.groupphase.backend.entity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class Time {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Employee employee;

    @Column(nullable = false)
    private LocalDateTime start;

    @Column(nullable = false)
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Time time = (Time) o;
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
        return "Time{" +
            "id='" + id + '\'' +
            ", employee=" + employee +
            ", start=" + start +
            ", end=" + end +
            '}';
    }

    public static final class TimeBuilder {
        private Long id;
        private Employee employee;
        private LocalDateTime start;
        private LocalDateTime end;

        private TimeBuilder() {
        }

        public static TimeBuilder aTime() {
            return new TimeBuilder();
        }

        public TimeBuilder withId(Long id) {
            this.id = id;
            return this;
        }

        public TimeBuilder withEmployee(Employee employee) {
            this.employee = employee;
            return this;
        }

        public TimeBuilder withStart(LocalDateTime start) {
            this.start = start;
            return this;
        }

        public TimeBuilder withEnd(LocalDateTime end) {
            this.end = end;
            return this;
        }


        public Time build() {
            Time time = new Time();
            time.setId(id);
            time.setEmployee(employee);
            time.setStart(start);
            time.setEnd(end);
            return time;
        }
    }


}
