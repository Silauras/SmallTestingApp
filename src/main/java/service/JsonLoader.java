package service;


import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.stream.Collectors;

public class JsonLoader {

    private static JsonLoader instance;

    public static JsonLoader getInstance() {
        if (instance == null) {
            instance = new JsonLoader();
            instance.logger.info("instance created");
        }
        return instance;
    }

    private final Logger logger = Logger.getLogger(JsonLoader.class);

    public Optional<String> getContent(String url) {
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setConnectTimeout(10000);
            connection.setRequestMethod("GET");
            if (connection.getResponseCode() != 200) {
                logger.error("connection failed on url: " + url + " with response code " + connection.getResponseCode());
                return Optional.empty();
            }
            else{
                logger.info("successful connection on url " + url + " with response code 200");
            }
            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
                return Optional.of(reader.lines().collect(Collectors.joining(System.lineSeparator())));
            }

        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
        return Optional.empty();
    }
}

