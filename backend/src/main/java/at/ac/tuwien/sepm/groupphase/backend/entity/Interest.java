package at.ac.tuwien.sepm.groupphase.backend.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Interest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 100)
    private String name;
    @Column(length = 1000)
    private String description;
    @ManyToOne
    private InterestArea interestArea;
    @ManyToOne(fetch = FetchType.EAGER)
    private Employee employee;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public InterestArea getInterestArea() {
        return interestArea;
    }

    public void setInterestArea(InterestArea interestArea) {
        this.interestArea = interestArea;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Interest interest = (Interest) o;
        return Objects.equals(id, interest.id) &&
            Objects.equals(name, interest.name) &&
            Objects.equals(description, interest.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description);
    }

    @Override
    public String toString() {
        return "Interest{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", description='" + description + '\'' +
            ", interestArea=" + interestArea.getId() +
            ", employee=" + employee +
            '}';
    }

    public static final class InterestBuilder {
        private Long id;
        private String name;
        private String description;
        private InterestArea interestArea;
        private Employee employee;

        private InterestBuilder() {
        }

        public static InterestBuilder aInterest() {
            return new InterestBuilder();
        }

        public InterestBuilder withId(Long id) {
            this.id = id;
            return this;
        }

        public InterestBuilder withDescription(String description) {
            this.description = description;
            return this;
        }

        public InterestBuilder withName(String name) {
            this.name = name;
            return this;
        }

        public InterestBuilder withInterestArea(InterestArea interestArea) {
            this.interestArea = interestArea;
            return this;
        }

        public InterestBuilder withEmployee(Employee employee) {
            this.employee = employee;
            return this;
        }

        public Interest build() {
            Interest interest = new Interest();
            interest.setId(id);
            interest.setDescription(description);
            interest.setInterestArea(interestArea);
            interest.setName(name);
            interest.setEmployee(employee);
            return interest;
        }
    }
}
