package Entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.math.BigDecimal;

@Embeddable
public class GeoPosition {
    public GeoPosition() {
    }

    public GeoPosition(BigDecimal latitude, BigDecimal longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @Column(name = "geo_lat")
    BigDecimal latitude;

    @Column(name = "geo_lng")
    BigDecimal longitude;

    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GeoPosition)) return false;

        GeoPosition that = (GeoPosition) o;

        if (latitude != null ? !latitude.equals(that.latitude) : that.latitude != null) return false;
        return longitude != null ? longitude.equals(that.longitude) : that.longitude == null;
    }

    @Override
    public int hashCode() {
        int result = latitude != null ? latitude.hashCode() : 0;
        result = 31 * result + (longitude != null ? longitude.hashCode() : 0);
        return result;
    }
}
