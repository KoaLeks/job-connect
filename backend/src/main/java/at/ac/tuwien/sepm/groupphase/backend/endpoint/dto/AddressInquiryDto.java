package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto;

import com.sun.istack.Nullable;

import javax.validation.constraints.*;
import java.util.Objects;

public class AddressInquiryDto {

    private Long id;

    @NotNull(message = "must not be null")
    @NotBlank(message = "must not be empty")
    private String city;

    @NotNull(message = "must not be null")
    @NotBlank(message = "must not be empty")
    private String state;

    @NotNull(message = "must not be null")
    @Min(value = 1000, message = "ZIP must be between 1000-9999")
    @Max(value = 9999, message = "ZIP must be between 1000-9999")
    private Integer zip;

    @NotNull(message = "must not be null")
    @NotBlank(message = "must not be empty")
    private String addressLine;

    @Nullable
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

    @AssertTrue(message = "only austrian states are valid")
    public boolean isValidState() {
        if (getState() != null) {
            return getState().equals("Vienna") ||
                getState().equals("Lower Austria") ||
                getState().equals("Upper Austria") ||
                getState().equals("Styria") ||
                getState().equals("Tyrol") ||
                getState().equals("Carinthia") ||
                getState().equals("Salzburg") ||
                getState().equals("Vorarlberg") ||
                getState().equals("Burgenland");
        } else {
            return false;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AddressInquiryDto address = (AddressInquiryDto) o;
        return Objects.equals(id, address.id) &&
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
        return "AddressInquiryDto{" +
            "id=" + id +
            ", city='" + city + '\'' +
            ", state='" + state + '\'' +
            ", zip=" + zip +
            ", addressLine='" + addressLine + '\'' +
            ", additional='" + additional + '\'' +
            '}';
    }

    public static final class AddressInquiryDtoBuilder {
        private Long id;
        private String city;
        private String state;
        private Integer zip;
        private String addressLine;
        private String additional;

        private AddressInquiryDtoBuilder() {
        }

        public static AddressInquiryDtoBuilder aAddress() {
            return new AddressInquiryDtoBuilder();
        }

        public AddressInquiryDtoBuilder withId(Long id) {
            this.id = id;
            return this;
        }

        public AddressInquiryDtoBuilder withCity(String city) {
            this.city = city;
            return this;
        }

        public AddressInquiryDtoBuilder withState(String state) {
            this.state = state;
            return this;
        }

        public AddressInquiryDtoBuilder withZip(Integer zip) {
            this.zip = zip;
            return this;
        }

        public AddressInquiryDtoBuilder withAddressLine(String addressLine) {
            this.addressLine = addressLine;
            return this;
        }

        public AddressInquiryDtoBuilder withAdditional(String additional) {
            this.additional = additional;
            return this;
        }

        public AddressInquiryDto build() {
            AddressInquiryDto address = new AddressInquiryDto();
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
