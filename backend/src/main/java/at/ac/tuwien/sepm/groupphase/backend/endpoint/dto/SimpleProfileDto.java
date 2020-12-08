package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto;

public class SimpleProfileDto {

    private Long id;
    private String lastName;
    private String firstName;
    private String email;
    private String publicInfo;
    private Byte[] picture;

    @Override
    public String toString() {
        return "SimpleProfileDto{" +
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

    public Byte[] getPicture() {
        return picture;
    }

    public void setPicture(Byte[] picture) {
        this.picture = picture;
    }

    public static final class SimpleProfileDtoBuilder {
        private Long id;
        private String lastName;
        private String firstName;
        private String email;
        private String publicInfo;
        private Byte[] picture;

        private SimpleProfileDtoBuilder() {
        }

        public static SimpleProfileDtoBuilder aSimpleProfileDto() {
            return new SimpleProfileDtoBuilder();
        }

        public SimpleProfileDtoBuilder withId(Long id) {
            this.id = id;
            return this;
        }

        public SimpleProfileDtoBuilder withLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public SimpleProfileDtoBuilder withFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public SimpleProfileDtoBuilder withEmail(String email) {
            this.email = email;
            return this;
        }

        public SimpleProfileDtoBuilder withPublicInfo(String publicInfo) {
            this.publicInfo = publicInfo;
            return this;
        }

        public SimpleProfileDtoBuilder withPicture(Byte[] picture) {
            this.picture = picture;
            return this;
        }

        public SimpleProfileDto build() {
            SimpleProfileDto simpleProfileDto = new SimpleProfileDto();
            simpleProfileDto.setId(id);
            simpleProfileDto.setLastName(lastName);
            simpleProfileDto.setFirstName(firstName);
            simpleProfileDto.setEmail(email);
            simpleProfileDto.setPublicInfo(publicInfo);
            simpleProfileDto.setPicture(picture);
            return simpleProfileDto;
        }
    }
}
