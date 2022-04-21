package entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import java.util.Objects;

@Embeddable
public class Address {

    public Address() {
    }

    public Address(String street, String suite, String city, String zipCode, GeoPosition geoPosition) {
        this.street = street;
        this.suite = suite;
        this.city = city;
        this.zipCode = zipCode;
        this.geoPosition = geoPosition;
    }

    @Column(name = "street")
            @JsonProperty("street")
    String street;

    @Column(name = "suite")
            @JsonProperty("suite")
    String suite;

    @Column(name = "city")
            @JsonProperty("city")
    String city;

    @Column(name = "zipcode")
    @JsonProperty("zipcode")
    String zipCode;

    @Embedded
    @JsonProperty("geo")
    GeoPosition geoPosition;

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getSuite() {
        return suite;
    }

    public void setSuite(String suite) {
        this.suite = suite;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public GeoPosition getGeoPosition() {
        return geoPosition;
    }

    public void setGeoPosition(GeoPosition geoPosition) {
        this.geoPosition = geoPosition;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Address)) return false;

        Address address = (Address) o;

        if (!Objects.equals(street, address.street)) return false;
        if (!Objects.equals(suite, address.suite)) return false;
        if (!Objects.equals(city, address.city)) return false;
        if (!Objects.equals(zipCode, address.zipCode)) return false;
        return Objects.equals(geoPosition, address.geoPosition);
    }

    @Override
    public int hashCode() {
        int result = street != null ? street.hashCode() : 0;
        result = 31 * result + (suite != null ? suite.hashCode() : 0);
        result = 31 * result + (city != null ? city.hashCode() : 0);
        result = 31 * result + (zipCode != null ? zipCode.hashCode() : 0);
        result = 31 * result + (geoPosition != null ? geoPosition.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Address{" +
                "street='" + street + '\'' +
                ", suite='" + suite + '\'' +
                ", city='" + city + '\'' +
                ", zipCode='" + zipCode + '\'' +
                ", geoPosition=" + geoPosition +
                '}';
    }
}
