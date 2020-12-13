package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Future;
import java.time.LocalDateTime;
import java.util.Objects;

public class TimeDto {

    private Long id;
    private LocalDateTime start;
    private LocalDateTime end;
    private LocalDateTime finalEndDate;
    private Boolean visible;
    private Long ref_id;

    public Long getRef_id() {
        return ref_id;
    }

    public void setRef_id(Long ref_id) {
        this.ref_id = ref_id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }

    public LocalDateTime getFinalEndDate() {
        return finalEndDate;
    }

    public void setFinalEndDate(LocalDateTime finalEndDate) {
        this.finalEndDate = finalEndDate;
    }

    public Boolean getVisible() {
        return visible;
    }

    @AssertTrue(message = "Start Zeitpunkt muss vor dem End Zeitpunkt liegen")
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
            start.equals(time.start) &&
            finalEndDate.equals(time.finalEndDate) &&
            visible.equals(time.visible) &&
            ref_id.equals(time.ref_id) &&
            end.equals(time.end);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, start, end, visible, finalEndDate, ref_id);
    }

    @Override
    public String toString() {
        return "TimeDto{" +
            "id='" + id + '\'' +
            ", start=" + start +
            ", end=" + end +
            ", visible=" + visible +
            ", finalEndDate=" + finalEndDate +
            ", ref_id=" + ref_id +
            '}';
    }

    public static final class TimeDtoBuilder {
        private Long id;
        private LocalDateTime start;
        private LocalDateTime end;
        private LocalDateTime finalEndDate;
        private Boolean visible;
        private Long ref_id;

        private TimeDtoBuilder() {
        }

        public static TimeDtoBuilder aTimeDto() {
            return new TimeDtoBuilder();
        }

        public TimeDtoBuilder withId(Long id) {
            this.id = id;
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

        public TimeDtoBuilder withFinalEndDate(LocalDateTime finalEndDate) {
            this.finalEndDate = finalEndDate;
            return this;
        }

        public TimeDtoBuilder withVisible(Boolean visible) {
            this.visible = visible;
            return this;
        }

        public TimeDtoBuilder withRef_Id(Long ref_id) {
            this.ref_id = ref_id;
            return this;
        }

        public TimeDto build() {
            TimeDto time = new TimeDto();
            time.setId(id);
            time.setStart(start);
            time.setEnd(end);
            time.setVisible(visible);
            time.setFinalEndDate(finalEndDate);
            time.setRef_id(ref_id);
            return time;
        }
    }


}
