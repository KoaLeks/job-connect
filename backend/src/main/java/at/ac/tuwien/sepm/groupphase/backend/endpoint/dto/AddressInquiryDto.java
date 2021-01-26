package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto;

import com.sun.istack.Nullable;

import javax.validation.constraints.*;
import java.util.Objects;

public class AddressInquiryDto {

    private Long id;

    @NotNull(message = "Stadt muss angegeben sein")
    @NotBlank(message = "Stadt darf nicht leer sein")
    private String city;

    @NotNull(message = "Bundesland muss angegeben sein")
    @NotBlank(message = "Bundesland darf nicht leer sein")
    private String state;

    @NotNull(message = "PLZ muss angegeben sein")
    @Min(value = 1000, message = "PLZ muss zwischen 1000 und 9999 liegen")
    @Max(value = 9999, message = "PLZ muss zwischen 1000 und 9999 liegen")
    private Integer zip;

    @NotNull(message = "Addresse muss angegeben sein")
    @NotBlank(message = "Addresse darf nicht leer sein")
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

    @AssertTrue(message = "Nur österreichische Bundesländer sind gültig")
    public boolean isValidState() {
        if (getState() != null) {
            return getState().equals("Wien") ||
                getState().equals("Niederösterreich") ||
                getState().equals("Oberösterreich") ||
                getState().equals("Steiermark") ||
                getState().equals("Tirol") ||
                getState().equals("Kärnten") ||
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
