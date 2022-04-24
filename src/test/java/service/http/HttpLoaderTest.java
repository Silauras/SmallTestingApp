package service.http;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class HttpLoaderTest {
    private final HttpLoader httpLoader = HttpLoader.getInstance();

    @Test
    public void testSiteWith200Status() {
        Assertions.assertTrue(httpLoader.getContent("https://google.com").isPresent());
    }

    @Test
    public void testSiteWithout200Status() {
        Assertions.assertTrue(httpLoader.getContent("https://google.com/404").isEmpty());
    }

    @Test
    public void testInvalidUrl(){
        Assertions.assertTrue(httpLoader.getContent("https://g.cm/404").isEmpty());
        Assertions.assertTrue(httpLoader.getContent("ht://g.cm/404").isEmpty());
    }

}
