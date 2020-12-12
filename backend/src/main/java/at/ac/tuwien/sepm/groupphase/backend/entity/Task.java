package at.ac.tuwien.sepm.groupphase.backend.entity;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 1000)
    private String description;
    @Column(nullable = false)
    private Integer employeeCount;
    @Column(nullable = false)
    private Double paymentHourly;
    @ManyToOne
    private Event event;
    @OneToMany(mappedBy = "task")
    private Set<Employee_Tasks> employees;
    @ManyToOne
    private InterestArea interestArea;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getEmployeeCount() {
        return employeeCount;
    }

    public void setEmployeeCount(Integer employeeCount) {
        this.employeeCount = employeeCount;
    }

    public Double getPaymentHourly() {
        return paymentHourly;
    }

    public void setPaymentHourly(Double paymentHourly) {
        this.paymentHourly = paymentHourly;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public Set<Employee_Tasks> getEmployees() {
        return employees;
    }

    public void setEmployees(Set<Employee_Tasks> employees) {
        this.employees = employees;
    }

    public InterestArea getInterestArea() {
        return interestArea;
    }

    public void setInterestArea(InterestArea interestArea) {
        this.interestArea = interestArea;
    }


    @Override
    public String toString() {
        return "Task{" +
            "id=" + id +
            ", description='" + description + '\'' +
            ", employeeCount=" + employeeCount +
            ", paymentHourly=" + paymentHourly +
            ", event=" + event +
            ", employees=" + employees +
            ", interestArea=" + interestArea +
            '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return Objects.equals(id, task.id) &&
            Objects.equals(description, task.description) &&
            Objects.equals(employeeCount, task.employeeCount) &&
            Objects.equals(paymentHourly, task.paymentHourly) &&
            Objects.equals(event, task.event) &&
            Objects.equals(employees, task.employees) &&
            Objects.equals(interestArea, task.interestArea);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description, employeeCount, paymentHourly, event, employees, interestArea);
    }

    public static final class TaskBuilder{
        private Long id;
        private String description;
        private Integer employeeCount;
        private Double paymentHourly;
        private Event event;
        private Set<Employee_Tasks> employees;
        private InterestArea interestArea;

        private TaskBuilder(){}
        public static TaskBuilder aTask(){
            return new TaskBuilder();
        }
        public TaskBuilder withId(Long id){
            this.id = id;
            return this;
        }
        public TaskBuilder withDescription(String description){
            this.description = description;
            return this;
        }
        public TaskBuilder withEmployeeCount(Integer employeeCount){
            this.employeeCount = employeeCount;
            return this;
        }
        public TaskBuilder withPaymentHourly(Double paymentHourly){
            this.paymentHourly = paymentHourly;
            return this;
        }
        public TaskBuilder withEvent(Event event){
            this.event = event;
            return this;
        }
        public TaskBuilder withEmployees(Set<Employee_Tasks> employees){
            this.employees = employees;
            return this;
        }
        public TaskBuilder withInterestArea(InterestArea interestArea){
            this.interestArea = interestArea;
            return this;
        }

        public Task build(){
            Task task = new Task();
            task.setDescription(description);
            task.setId(id);
            task.setEmployeeCount(employeeCount);
            task.setEmployees(employees);
            task.setEvent(event);
            task.setInterestArea(interestArea);
            task.setPaymentHourly(paymentHourly);
            return task;
        }
    }
}
