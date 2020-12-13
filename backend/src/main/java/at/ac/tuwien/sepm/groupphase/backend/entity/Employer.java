package at.ac.tuwien.sepm.groupphase.backend.entity;

import net.bytebuddy.implementation.bind.annotation.Empty;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Objects;
import java.util.Set;

@Entity
public class Employer {
    @Id
    private Long id;

    @OneToOne
    @MapsId
    @NotNull
    private Profile profile;

    @Column(nullable = false)
    @NotBlank
    private String companyName;

    @Column(length = 1000)
    private String description;

    @OneToMany(mappedBy = "employer")
    private Set<Event> events;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Event> getEvents() {
        return events;
    }

    public void setEvents(Set<Event> events) {
        this.events = events;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employer employer = (Employer) o;
        return Objects.equals(id, employer.id) &&
            Objects.equals(profile, employer.profile) &&
            Objects.equals(companyName, employer.companyName) &&
            Objects.equals(description, employer.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, profile, companyName, description);
    }

    @Override
    public String toString() {
        return "Employer{" +
            "id=" + id +
            ", profile=" + profile +
            ", companyName='" + companyName + '\'' +
            ", description='" + description + '\'' +
            //", events=" + events.size() +
            '}';
    }

    public static final class EmployerBuilder{
        private Long id;
        private Profile profile;
        private String companyName;
        private String description;
        private Set<Event> events;

        private EmployerBuilder(){}

        public static EmployerBuilder aEmployer(){
            return new EmployerBuilder();
        }
        public EmployerBuilder withProfile(Profile profile){
            this.id = profile.getId();
            this.profile = profile;
            return this;
        }
        public EmployerBuilder withCompanyName(String companyName){
            this.companyName = companyName;
            return this;
        }
        public EmployerBuilder withDescription(String description){
            this.description = description;
            return this;
        }
        public EmployerBuilder withEvents(Set<Event> events){
            this.events = events;
            return this;
        }

        public Employer build(){
            Employer employer = new Employer();
            employer.setId(id);
            employer.setProfile(profile);
            employer.setCompanyName(companyName);
            employer.setDescription(description);
            employer.setEvents(events);
            return employer;
        }
    }
}
