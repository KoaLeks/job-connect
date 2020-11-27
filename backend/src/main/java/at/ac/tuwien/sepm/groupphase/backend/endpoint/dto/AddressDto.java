package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto;

import java.util.Objects;

public class AddressDto {

    private Long id;
    private String city;
    private String state;
    private Integer zip;
    private String addressLine;
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
        AddressDto address = (AddressDto) o;
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
        return "AddressDto{" +
            "id=" + id +
            ", city='" + city + '\'' +
            ", state='" + state + '\'' +
            ", zip='" + zip + '\'' +
            ", addressLine='" + addressLine + '\'' +
            ", additional='" + additional + '\'' +
            '}';
    }

    public static final class AddressDtoBuilder {
        private Long id;
        private String city;
        private String state;
        private Integer zip;
        private String addressLine;
        private String additional;

        private AddressDtoBuilder() {
        }

        public static AddressDtoBuilder aAddress() {
            return new AddressDtoBuilder();
        }

        public AddressDtoBuilder withId(Long id) {
            this.id = id;
            return this;
        }

        public AddressDtoBuilder withCity(String city) {
            this.city = city;
            return this;
        }

        public AddressDtoBuilder withState(String state) {
            this.state = state;
            return this;
        }

        public AddressDtoBuilder withZip(Integer zip) {
            this.zip = zip;
            return this;
        }

        public AddressDtoBuilder withAddressLine(String addressLine) {
            this.addressLine = addressLine;
            return this;
        }

        public AddressDtoBuilder withAdditional(String additional) {
            this.additional = additional;
            return this;
        }

        public AddressDto build() {
            AddressDto address = new AddressDto();
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
