package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto;

import java.util.Objects;

public class SimpleInterestAreaDto {

    private Long id;
    private String area;
    private String description;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SimpleInterestAreaDto that = (SimpleInterestAreaDto) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, area, description);
    }

    @Override
    public String toString() {
        return "InterestAreaDto{" +
            "id='" + id + '\'' +
            ", area='" + area + '\'' +
            ", description='" + description + '\'' +
            '}';
    }

    public static final class SimpleInterestAreaDtoBuilder {
        private Long id;
        private String area;
        private String description;


        private SimpleInterestAreaDtoBuilder() {
        }

        public static SimpleInterestAreaDtoBuilder aInterestArea() {
            return new SimpleInterestAreaDtoBuilder();
        }


        public SimpleInterestAreaDtoBuilder withId(Long id) {
            this.id = id;
            return this;
        }

        public SimpleInterestAreaDtoBuilder withArea(String area) {
            this.area = area;
            return this;
        }

        public SimpleInterestAreaDtoBuilder withDescription(String description) {
            this.description = description;
            return this;
        }

        public SimpleInterestAreaDto build() {
            SimpleInterestAreaDto simpleInterestAreaDto = new SimpleInterestAreaDto();
            simpleInterestAreaDto.setId(id);
            simpleInterestAreaDto.setArea(area);
            simpleInterestAreaDto.setDescription(description);
            return simpleInterestAreaDto;
        }
    }
}
