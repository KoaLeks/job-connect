
package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto;

import java.util.Set;

public class FilterEmployeesSmartDto {

    private Set<Long> eventIds;

    public Set<Long> getEventIds() {
        return eventIds;
    }

    public void setEventIds(Set<Long> eventIds) {
        this.eventIds = eventIds;
    }

    public static final class FilterEmployeesSmartDtoBuilder{
        private Set<Long> eventIds;

        private FilterEmployeesSmartDtoBuilder(){ }

        public static FilterEmployeesSmartDtoBuilder aFilterEmployeesSmartDto(){
            return new FilterEmployeesSmartDtoBuilder();
        }

        public FilterEmployeesSmartDtoBuilder withEventIds(Set<Long> eventIds){
            this.eventIds = eventIds;
            return this;
        }

        public FilterEmployeesSmartDto build(){
            FilterEmployeesSmartDto filterEmployeesSmartDto = new FilterEmployeesSmartDto();
            filterEmployeesSmartDto.setEventIds(this.eventIds);
            return filterEmployeesSmartDto;
        }
    }
}

