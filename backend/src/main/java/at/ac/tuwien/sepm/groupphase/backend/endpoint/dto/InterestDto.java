package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto;

import java.util.Objects;

public class InterestDto {
    private Long id;

    private String name;

    private String description;

    private SimpleInterestAreaDto simpleInterestAreaDto;

    //private Set<Employee> employees;

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

    public SimpleInterestAreaDto getSimpleInterestAreaDto() {
        return simpleInterestAreaDto;
    }

    public void setSimpleInterestAreaDto(SimpleInterestAreaDto simpleInterestAreaDto) {
        this.simpleInterestAreaDto = simpleInterestAreaDto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof InterestDto)) return false;
        InterestDto that = (InterestDto) o;
        return Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(description, that.description) &&
            Objects.equals(simpleInterestAreaDto, that.simpleInterestAreaDto);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, simpleInterestAreaDto);
    }

    @Override
    public String toString() {
        return "InterestDto{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", description='" + description + '\'' +
            ", simpleInterestAreaDto=" + simpleInterestAreaDto +
            '}';
    }

    public static final class InterestDtoBuilder {
        private Long id;
        private String name;
        private String description;
        private SimpleInterestAreaDto simpleInterestAreaDto;

        private InterestDtoBuilder() {
        }

        public static InterestDto.InterestDtoBuilder aInterestDto() {
            return new InterestDto.InterestDtoBuilder();
        }

        public InterestDto.InterestDtoBuilder withId(Long id) {
            this.id = id;
            return this;
        }

        public InterestDto.InterestDtoBuilder withName(String name) {
            this.name = name;
            return this;
        }

        public InterestDto.InterestDtoBuilder withDescription(String description) {
            this.description = description;
            return this;
        }

        public InterestDto.InterestDtoBuilder withSimpleInterestAreaDto(SimpleInterestAreaDto simpleInterestAreaDto) {
            this.simpleInterestAreaDto = simpleInterestAreaDto;
            return this;
        }

        public InterestDto build() {
            InterestDto interestDto = new InterestDto();
            interestDto.setId(id);
            interestDto.setName(name);
            interestDto.setDescription(description);
            interestDto.setSimpleInterestAreaDto(simpleInterestAreaDto);
            return interestDto;
        }
    }
}
