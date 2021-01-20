package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto;

public class SimpleEmployerDto {

    private Long id;
    private SimpleProfileDto simpleProfileDto;
    private String companyName;
    private String companyDescription;

    @Override
    public String toString() {
        return "SimpleEmployerDto{" +
            "id=" + id +
            ", profileDto=" + simpleProfileDto +
            ", companyName='" + companyName + '\'' +
            ", companyDescription='" + companyDescription + '\'' +
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

    public String getCompanyDescription() {
        return companyDescription;
    }

    public void setCompanyDescription(String description) {
        this.companyDescription = description;
    }

    public static final class SimpleEmployerDtoBuilder {
        private Long id;
        private SimpleProfileDto simpleProfileDto;
        private String companyName;
        private String companyDescription;

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

        public SimpleEmployerDtoBuilder withCompanyDescription(String description) {
            this.companyDescription = description;
            return this;
        }

        public SimpleEmployerDto build() {
            SimpleEmployerDto simpleEmployerDto = new SimpleEmployerDto();
            simpleEmployerDto.setId(id);
            simpleEmployerDto.setSimpleProfileDto(simpleProfileDto);
            simpleEmployerDto.setCompanyName(companyName);
            simpleEmployerDto.setCompanyDescription(companyDescription);
            return simpleEmployerDto;
        }
    }
}
