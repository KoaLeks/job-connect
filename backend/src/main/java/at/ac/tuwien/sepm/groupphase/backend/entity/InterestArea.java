package at.ac.tuwien.sepm.groupphase.backend.entity;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
public class InterestArea {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 100)
    private String area;
    @Column(length = 1000)
    private String description;
    @OneToMany(mappedBy = "interestArea")
    private Set<Interest> interests;
    @OneToMany(mappedBy = "interestArea")
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
        InterestArea that = (InterestArea) o;
        return Objects.equals(id, that.id) &&
            Objects.equals(area, that.area) &&
            Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, area, description);
    }

    @Override
    public String toString() {
        return "InterestArea{" +
            "id=" + id +
            ", area='" + area + '\'' +
            ", description='" + description + '\'' +
//            ", interests=" + (interests != null ? interests.size(): null) +
//            ", tasks=" + (tasks != null ? tasks.size(): null) +
            '}';
    }

    public static final class InterestAreaBuilder{
        private Long id;
        private String area;
        private String description;
        private Set<Interest> interests;
        private Set<Task> tasks;

        private InterestAreaBuilder(){
        }

        public static InterestAreaBuilder aInterest(){
            return new InterestAreaBuilder();
        }

        public InterestAreaBuilder withId(Long id){
            this.id = id;
            return this;
        }
        public InterestAreaBuilder withArea(String area){
            this.area = area;
            return this;
        }
        public InterestAreaBuilder withDescription(String description){
            this.description = description;
            return this;
        }
        public InterestAreaBuilder withInterests(Set<Interest> interests){
            this.interests = interests;
            return this;
        }
        public InterestAreaBuilder withTasks(Set<Task> tasks){
            this.tasks = tasks;
            return this;
        }

        public InterestArea build(){
            InterestArea interestArea = new InterestArea();
            interestArea.setId(id);
            interestArea.setArea(area);
            interestArea.setDescription(description);
            interestArea.setInterests(interests);
            interestArea.setTasks(tasks);
            return interestArea;
        }
    }
}
