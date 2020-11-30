package at.ac.tuwien.sepm.groupphase.backend.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String city;
    @Column(nullable = false)
    private String state;
    @Column(nullable = false)
    private Integer zip;
    @Column(nullable = false)
    private String addressLine;
    @Column
    private String additional;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Integer getZip() {
        return zip;
    }

    public void setZip(Integer zip) {
        this.zip = zip;
    }

    public String getAddressLine() {
        return addressLine;
    }

    public void setAddressLine(String addressLine) {
        this.addressLine = addressLine;
    }

    public String getAdditional() {
        return additional;
    }

    public void setAdditional(String additional) {
        this.additional = additional;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return id == address.id &&
            Objects.equals(city, address.city) &&
            Objects.equals(state, address.state) &&
            Objects.equals(zip, address.zip) &&
            Objects.equals(addressLine, address.addressLine) &&
            Objects.equals(additional, address.additional);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, city, state, zip, addressLine, additional);
    }

    @Override
    public String toString() {
        return "Address{" +
            "id=" + id +
            ", city='" + city + '\'' +
            ", state='" + state + '\'' +
            ", zip='" + zip + '\'' +
            ", addressLine='" + addressLine + '\'' +
            ", additional='" + additional + '\'' +
            '}';
    }

    public static final class AddressBuilder {
        private Long id;
        private String city;
        private String state;
        private Integer zip;
        private String addressLine;
        private String additional;

        private AddressBuilder() {
        }

        public static AddressBuilder aAddress() {
            return new AddressBuilder();
        }

        public AddressBuilder withId(Long id) {
            this.id = id;
            return this;
        }

        public AddressBuilder withCity(String city) {
            this.city = city;
            return this;
        }

        public AddressBuilder withState(String state) {
            this.state = state;
            return this;
        }

        public AddressBuilder withZip(Integer zip) {
            this.zip = zip;
            return this;
        }

        public AddressBuilder withAddressLine(String addressLine) {
            this.addressLine = addressLine;
            return this;
        }

        public AddressBuilder withAdditional(String additional) {
            this.additional = additional;
            return this;
        }

        public Address build() {
            Address address = new Address();
            address.setId(id);
            address.setCity(city);
            address.setZip(zip);
            address.setState(state);
            address.setAdditional(additional);
            address.setAddressLine(addressLine);
            return address;
        }
    }
}
