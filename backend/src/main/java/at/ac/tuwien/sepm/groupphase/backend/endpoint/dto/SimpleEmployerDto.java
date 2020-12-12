package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto;

public class SimpleEmployerDto {

    private Long id;
    private SimpleProfileDto simpleProfileDto;
    private String companyName;
    private String description;

    @Override
    public String toString() {
        return "SimpleEmployerDto{" +
            "id=" + id +
            ", profileDto=" + simpleProfileDto +
            ", companyName='" + companyName + '\'' +
            ", description='" + description + '\'' +
            '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SimpleProfileDto getSimpleProfileDto() {
        return simpleProfileDto;
    }

    public void setSimpleProfileDto(SimpleProfileDto simpleProfileDto) {
        this.simpleProfileDto = simpleProfileDto;
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

    public static final class SimpleEmployerDtoBuilder {
        private Long id;
        private SimpleProfileDto simpleProfileDto;
        private String companyName;
        private String description;

        private SimpleEmployerDtoBuilder() {
        }

        public static SimpleEmployerDtoBuilder aSimpleEmployerDto() {
            return new SimpleEmployerDtoBuilder();
        }

        public SimpleEmployerDtoBuilder withProfileDto(SimpleProfileDto profileDto) {
            this.id = profileDto.getId();
            this.simpleProfileDto = profileDto;
            return this;
        }

        public SimpleEmployerDtoBuilder withCompanyName(String companyName) {
            this.companyName = companyName;
            return this;
        }

        public SimpleEmployerDtoBuilder withDescription(String description) {
            this.description = description;
            return this;
        }

        public SimpleEmployerDto build() {
            SimpleEmployerDto simpleEmployerDto = new SimpleEmployerDto();
            simpleEmployerDto.setId(id);
            simpleEmployerDto.setSimpleProfileDto(simpleProfileDto);
            simpleEmployerDto.setCompanyName(companyName);
            simpleEmployerDto.setDescription(description);
            return simpleEmployerDto;
        }
    }
}
