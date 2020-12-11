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

    @Column
    private Boolean visible;

    @Column
    private LocalDateTime finalEndDate;

    public Boolean getVisible() {
        return visible;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }

    public LocalDateTime getFinalEndDate() {
        return finalEndDate;
    }

    public void setFinalEndDate(LocalDateTime finalEndDate) {
        this.finalEndDate = finalEndDate;
    }

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
            finalEndDate.equals(time.finalEndDate) &&
            visible.equals(time.visible) &&
            end.equals(time.end);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, employee, start, end, visible, finalEndDate);
    }

    @Override
    public String toString() {
        return "Time{" +
            "id='" + id + '\'' +
            ", employee=" + employee +
            ", start=" + start +
            ", end=" + end +
            ", visible=" + visible +
            ", finalEndDate=" + finalEndDate +
            '}';
    }

    public static final class TimeBuilder {
        private Long id;
        private Employee employee;
        private LocalDateTime start;
        private LocalDateTime end;
        private LocalDateTime finalEndDate;
        private Boolean visible;

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

        public TimeBuilder withFinalEndDate(LocalDateTime finalEndDate) {
            this.finalEndDate = finalEndDate;
            return this;
        }

        public TimeBuilder withVisible(Boolean visible) {
            this.visible = visible;
            return this;
        }

        public Time build() {
            Time time = new Time();
            time.setId(id);
            time.setEmployee(employee);
            time.setStart(start);
            time.setEnd(end);
            time.setVisible(visible);
            time.setFinalEndDate(finalEndDate);
            return time;
        }
    }


}
