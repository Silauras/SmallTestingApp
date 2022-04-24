package service.http;


import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.stream.Collectors;

public class HttpLoader {

    private static HttpLoader instance;

    public static HttpLoader getInstance() {
        if (instance == null) {
            instance = new HttpLoader();
            instance.logger.info("instance created");
        }
        return instance;
    }

    private final Logger logger = Logger.getLogger(HttpLoader.class);
    private final int CONNECTION_TIMEOUT = 10000;

    public Optional<String> getContent(String url) {
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setConnectTimeout(CONNECTION_TIMEOUT);
            connection.setRequestMethod("GET");
            if (connection.getResponseCode() != 200) {
                logger.error("connection failed on url: " + url + " with response code " + connection.getResponseCode());
                return Optional.empty();
            } else {
                logger.info("successful connection on url " + url + " with response code 200");
            }
            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
                return Optional.of(reader.lines().collect(Collectors.joining(System.lineSeparator())));
            }

        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
        return Optional.empty();
    }
}

