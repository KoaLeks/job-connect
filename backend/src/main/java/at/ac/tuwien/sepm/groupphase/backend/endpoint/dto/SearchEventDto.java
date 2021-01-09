package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto;

import java.time.LocalDateTime;

public class SearchEventDto {

    private String title;
    private Long interestAreaId;
    private Long employerId;
    private String start;
    private String end;
    private Long payment;
    private boolean onlyAvailableTasks;
    private Long userId;

    public SearchEventDto() {
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getInterestAreaId() {
        return interestAreaId;
    }

    public void setInterestAreaId(Long interestAreaId) {
        this.interestAreaId = interestAreaId;
    }

    public Long getEmployerId() {
        return employerId;
    }

    public void setEmployerId(Long employerId) {
        this.employerId = employerId;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public Long getPayment() {
        return payment;
    }

    public void setPayment(Long payment) {
        this.payment = payment;
    }

    public boolean isOnlyAvailableTasks() {
        return onlyAvailableTasks;
    }

    public void setOnlyAvailableTasks(boolean onlyAvailableTasks) {
        this.onlyAvailableTasks = onlyAvailableTasks;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "SearchEventDto{" +
            "title='" + title + '\'' +
            ", interestAreaId=" + interestAreaId +
            ", employerId=" + employerId +
            ", start=" + start +
            ", end=" + end +
            ", payment=" + payment +
            ", onlyAvailableTasks=" + onlyAvailableTasks +
            ", userId=" + userId +
            '}';
    }
}
