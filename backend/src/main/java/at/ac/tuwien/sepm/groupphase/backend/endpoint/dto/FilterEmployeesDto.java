package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Set;

public class FilterEmployeesDto {
    private Set<Long> interestAreas;

    @DateTimeFormat(pattern="yyyy-MM-dd'T'HH:mm")
    private Set<LocalDateTime> startTimes;

    public Set<Long> getInterestAreas() {
        return interestAreas;
    }

    public void setInterestAreas(Set<Long> interestAreas) {
        this.interestAreas = interestAreas;
    }

    public Set<LocalDateTime> getStartTimes() {
        return startTimes;
    }

    public void setStartTimes(Set<LocalDateTime> startTimes) {
        this.startTimes = startTimes;
    }

    public static final class FilterEmployerDtoBuilder{
        private Set<Long> interestAreas;
        private Set<LocalDateTime> startTimes;

        private FilterEmployerDtoBuilder(){}

        public static FilterEmployeesDto.FilterEmployerDtoBuilder aFilterEmployerDto(){
            return new FilterEmployerDtoBuilder();
        }

        public FilterEmployerDtoBuilder withInterestAreas(Set<Long> interestAreas){
            this.interestAreas = interestAreas;
            return this;
        }

        public FilterEmployerDtoBuilder withStartTimes(Set<LocalDateTime> startTimes){
            this.startTimes = startTimes;
            return this;
        }

        public FilterEmployeesDto build(){
            FilterEmployeesDto filterEmployeesDto = new FilterEmployeesDto();
            filterEmployeesDto.setInterestAreas(this.interestAreas);
            filterEmployeesDto.setStartTimes(this.startTimes);
            return filterEmployeesDto;
        }
    }
}
