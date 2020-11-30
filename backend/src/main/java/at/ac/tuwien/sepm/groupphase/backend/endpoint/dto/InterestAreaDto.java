package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto;

import at.ac.tuwien.sepm.groupphase.backend.entity.Interest;
import at.ac.tuwien.sepm.groupphase.backend.entity.Task;

import java.util.Objects;
import java.util.Set;

public class InterestAreaDto {

    private Long id;
    private String area;
    private String description;
    private Set<Interest> interests;
    private Set<Task> tasks;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Interest> getInterests() {
        return interests;
    }

    public void setInterests(Set<Interest> interests) {
        this.interests = interests;
    }

    public Set<Task> getTasks() {
        return tasks;
    }

    public void setTasks(Set<Task> tasks) {
        this.tasks = tasks;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InterestAreaDto that = (InterestAreaDto) o;
        return Objects.equals(id, that.id);

    }

    @Override
    public int hashCode() {
        return Objects.hash(id, area, description);
    }

    @Override
    public String toString() {
        return "InterestAreaDto{" +
            "id=" + id +
            ", area='" + area + '\'' +
            ", description='" + description + '\'' +
            ", interests=" + interests +
            ", tasks=" + tasks +
            '}';
    }

    public static final class InterestAreaDtoBuilder {
        private Long id;
        private String area;
        private String description;
        private Set<Interest> interests;
        private Set<Task> tasks;


        private InterestAreaDtoBuilder() {
        }

        public static InterestAreaDto.InterestAreaDtoBuilder aInterestArea() {
            return new InterestAreaDto.InterestAreaDtoBuilder();
        }

        public InterestAreaDto.InterestAreaDtoBuilder withId(Long id) {
            this.id = id;
            return this;
        }

        public InterestAreaDto.InterestAreaDtoBuilder withArea(String area) {
            this.area = area;
            return this;
        }

        public InterestAreaDto.InterestAreaDtoBuilder withDescription(String description) {
            this.description = description;
            return this;
        }

        public InterestAreaDto.InterestAreaDtoBuilder withInterests(Set<Interest> interests) {
            this.interests = interests;
            return this;
        }

        public InterestAreaDto.InterestAreaDtoBuilder withTasks(Set<Task> tasks) {
            this.tasks = tasks;
            return this;
        }

        public InterestAreaDto build() {
            InterestAreaDto interestAreaDto = new InterestAreaDto();
            interestAreaDto.setId(id);
            interestAreaDto.setArea(area);
            interestAreaDto.setDescription(description);
            interestAreaDto.setInterests(interests);
            interestAreaDto.setTasks(tasks);
            return interestAreaDto;
        }
    }
}
