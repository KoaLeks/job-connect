package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto;

public class SuperSimpleProfileDto {
    private Long id;
    private String lastName;
    private String firstName;
    private String email;
    private String publicInfo;

    @Override
    public String toString() {
        return "SuperSimpleProfileDto{" +
            "id=" + id +
            ", lastName='" + lastName + '\'' +
            ", firstName='" + firstName + '\'' +
            ", email='" + email + '\'' +
            ", publicInfo='" + publicInfo + '\'' +
            '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPublicInfo() {
        return publicInfo;
    }

    public void setPublicInfo(String publicInfo) {
        this.publicInfo = publicInfo;
    }

    public static final class SuperSimpleProfileDtoBuilder {
        private Long id;
        private String lastName;
        private String firstName;
        private String email;
        private String publicInfo;

        private SuperSimpleProfileDtoBuilder() {
        }

        public static SuperSimpleProfileDto.SuperSimpleProfileDtoBuilder aSuperSimpleProfileDto() {
            return new SuperSimpleProfileDto.SuperSimpleProfileDtoBuilder();
        }

        public SuperSimpleProfileDto.SuperSimpleProfileDtoBuilder withId(Long id) {
            this.id = id;
            return this;
        }

        public SuperSimpleProfileDto.SuperSimpleProfileDtoBuilder withLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public SuperSimpleProfileDto.SuperSimpleProfileDtoBuilder withFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public SuperSimpleProfileDto.SuperSimpleProfileDtoBuilder withEmail(String email) {
            this.email = email;
            return this;
        }

        public SuperSimpleProfileDto.SuperSimpleProfileDtoBuilder withPublicInfo(String publicInfo) {
            this.publicInfo = publicInfo;
            return this;
        }

        public SuperSimpleProfileDto build() {
            SuperSimpleProfileDto superSimpleProfileDto = new SuperSimpleProfileDto();
            superSimpleProfileDto.setId(id);
            superSimpleProfileDto.setLastName(lastName);
            superSimpleProfileDto.setFirstName(firstName);
            superSimpleProfileDto.setEmail(email);
            superSimpleProfileDto.setPublicInfo(publicInfo);
            return superSimpleProfileDto;
        }
    }
}

