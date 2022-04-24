package entity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonConverterTest {

    final static ObjectMapper mapper = new ObjectMapper();

    final static String JSON =
            "{ " +
                    "    \"id\": 20, " +
                    "    \"name\": \"Lean Grakh\", " +
                    "    \"username\": \"Bret\"," +
                    "    \"email\": \"Ssdincere@april.biz\"," +
                    "    \"address\": {" +
                    "      \"street\": \"Kulas Light\"," +
                    "      \"suite\": \"Apt. 556\"," +
                    "      \"city\": \"Gwenborough\"," +
                    "      \"zipcode\": \"92998-3874\"," +
                    "      \"geo\": {" +
                    "        \"lat\": \"-37.3159\"," +
                    "        \"lng\": \"81.1496\"" +
                    "      }" +
                    "    }," +
                    "    \"phone\": \"1-770-736-8031 x56442\"," +
                    "    \"website\": \"hildegard.org\"," +
                    "    \"company\": {" +
                    "      \"name\": \"Romaguera-Crona\"," +
                    "      \"catchPhrase\": \"Multi-layered client-server neural-net\"," +
                    "      \"bs\": \"harness real-time e-markets\"" +
                    "    }" +
                    "  }";

    @Test
    public void jsonToUserConvertationTest() throws JsonProcessingException {
        User user = mapper.readValue(JSON, User.class);

        assertEquals(20, user.getId());
        assertEquals("Lean Grakh", user.getName());
        assertEquals("Bret", user.getUsername());
        assertEquals("Ssdincere@april.biz", user.getEmail());
        assertEquals("1-770-736-8031 x56442", user.getPhone());
        assertEquals("hildegard.org", user.getWebsite());

        Address address = user.getAddress();

        assertEquals("Kulas Light", address.getStreet());
        assertEquals("Apt. 556", address.getSuite());
        assertEquals("Gwenborough", address.getCity());
        assertEquals("92998-3874", address.getZipCode());

        GeoPosition geoPosition = address.getGeoPosition();

        assertEquals(new BigDecimal("-37.3159"), geoPosition.getLatitude());
        assertEquals(new BigDecimal("81.1496"), geoPosition.getLongitude());

        Company company = user.getCompany();

        assertEquals("Romaguera-Crona", company.getName());
        assertEquals("Multi-layered client-server neural-net", company.getCatchPhrase());
        assertEquals("harness real-time e-markets", company.getBusinessStrategy());

    }
}
