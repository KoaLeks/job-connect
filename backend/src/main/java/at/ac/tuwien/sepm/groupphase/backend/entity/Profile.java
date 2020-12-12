package at.ac.tuwien.sepm.groupphase.backend.entity;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.Arrays;
import java.util.Objects;
import java.util.Set;

@Entity
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    @NotBlank
    private String lastName;

    @Column(nullable = false, length = 100)
    @NotBlank
    private String firstName;

    @Column(nullable = false, length = 100, unique = true)
    @Email
    private String email;

    @Column(nullable = false)
    @NotBlank
    private String password;

    @Column(length = 10000)
    private String publicInfo;

    //If user is an Employee or Employer
    @Column(nullable = false)
    private boolean employer;

    @OneToMany
    private Set<Notification> notifications;

    @Lob
    private Byte[] picture;

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPublicInfo() {
        return publicInfo;
    }

    public void setPublicInfo(String publicInfo) {
        this.publicInfo = publicInfo;
    }

    public boolean isEmployer() {
        return employer;
    }

    public void setEmployer(boolean employer) {
        this.employer = employer;
    }

    public Byte[] getPicture() {
        return picture;
    }

    public void setPicture(Byte[] picture) {
        this.picture = picture;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Profile profile = (Profile) o;
        return Objects.equals(id, profile.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Profile{" +
            "id=" + id +
            ", lastName='" + lastName + '\'' +
            ", firstName='" + firstName + '\'' +
            ", email='" + email + '\'' +
            ", password='" + password + '\'' +
            ", publicInfo='" + publicInfo + '\'' +
            ", employer=" + employer +
            ", picture=" + picture +
            '}';
    }

    public static final class ProfileBuilder {
        private Long id;
        private String lastName;
        private String firstName;
        private String email;
        private String password;
        private String publicInfo;
        private boolean employer;
        private Byte[] picture;

        private ProfileBuilder() {
        }

        public static ProfileBuilder aProfile() {
            return new ProfileBuilder();
        }

        public ProfileBuilder withId(Long id) {
            this.id = id;
            return this;
        }

        public ProfileBuilder withName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public ProfileBuilder withForename(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public ProfileBuilder withEmail(String email) {
            this.email = email;
            return this;
        }

        public ProfileBuilder withPassword(String password) {
            this.password = password;
            return this;
        }

        public ProfileBuilder withPublicInfo(String publicInfo) {
            this.publicInfo = publicInfo;
            return this;
        }

        public ProfileBuilder isEmployer(boolean employer) {
            this.employer = employer;
            return this;
        }

        public ProfileBuilder isPicture(Byte[] picture) {
            this.picture = picture;
            return this;
        }

        public Profile build() {
            Profile profile = new Profile();
            profile.setId(id);
            profile.setLastName(lastName);
            profile.setFirstName(firstName);
            profile.setEmail(email);
            profile.setPassword(password);
            profile.setPublicInfo(publicInfo);
            profile.setEmployer(employer);
            profile.setPicture(picture);
            return profile;
        }
    }
}
