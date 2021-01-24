package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto;

public class SuperSimpleEmployerDto {

    private Long id;
    private SuperSimpleProfileDto superSimpleProfileDto;
    private String companyName;
    private String companyDescription;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SuperSimpleProfileDto getSuperSimpleProfileDto() {
        return superSimpleProfileDto;
    }

    public void setSuperSimpleProfileDto(SuperSimpleProfileDto superSimpleProfileDto) {
        this.superSimpleProfileDto = superSimpleProfileDto;
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

    public void setCompanyDescription(String companyDescription) {
        this.companyDescription = companyDescription;
    }

    public static final class SuperSimpleEmployerDtoBuilder {
        private Long id;
        private SuperSimpleProfileDto superSimpleProfileDto;
        private String companyName;
        private String companyDescription;

        private SuperSimpleEmployerDtoBuilder() {
        }

        public static SuperSimpleEmployerDtoBuilder aSuperSimpleEmployerDto() {
            return new SuperSimpleEmployerDtoBuilder();
        }

        public SuperSimpleEmployerDtoBuilder withId(Long id) {
            this.id = id;
            return this;
        }

        public SuperSimpleEmployerDtoBuilder withSuperSimpleProfileDto(SuperSimpleProfileDto superSimpleProfileDto) {
            this.superSimpleProfileDto = superSimpleProfileDto;
            return this;
        }

        public SuperSimpleEmployerDtoBuilder withCompanyName(String companyName) {
            this.companyName = companyName;
            return this;
        }

        public SuperSimpleEmployerDtoBuilder withCompanyDescription(String companyDescription) {
            this.companyDescription = companyDescription;
            return this;
        }

        public SuperSimpleEmployerDto build() {
            SuperSimpleEmployerDto superSimpleEmployerDto = new SuperSimpleEmployerDto();
            superSimpleEmployerDto.setId(id);
            superSimpleEmployerDto.setSuperSimpleProfileDto(superSimpleProfileDto);
            superSimpleEmployerDto.setCompanyName(companyName);
            superSimpleEmployerDto.setCompanyDescription(companyDescription);
            return superSimpleEmployerDto;
        }
    }
}
