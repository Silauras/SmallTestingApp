import entity.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.validator.routines.EmailValidator;
import org.apache.log4j.Logger;
import service.http.HttpLoader;
import service.database.DatabaseManager;

import java.util.*;

public class Main {

    final static private String URL = "jdbc:postgresql:test";
    final static private String USER = "postgres";
    final static private String PASSWORD = "root";
    final static private String DRIVER = "org.postgresql.Driver";

    final static private String TARGET_URL = "https://jsonplaceholder.typicode.com/users";

    final static Logger logger = Logger.getLogger(Main.class);
    final static private EmailValidator emailValidator = EmailValidator.getInstance();
    final static private DatabaseManager dbManager = new DatabaseManager(URL, USER, PASSWORD, DRIVER);
    final static ObjectMapper mapper = new ObjectMapper();


    public static void main(final String[] args) {
        try {
            Optional<String> json = HttpLoader.getInstance().getContent(TARGET_URL);
            if (json.isPresent()) {
                User[] users = mapper.readValue(json.get(), User[].class);
                List<User> userList = new ArrayList<>();
                for (User user : users) {
                    if (emailValidator.isValid(user.getEmail())) {
                        userList.add(user);
                        logger.info("user with id:" + user.getId() + " with email: " + user.getEmail() + " has valid email");
                    } else {
                        logger.info("user with id:" + user.getId() + " with email: " + user.getEmail() + " has invalid email");
                    }

                }
                dbManager.saveObjects(userList.toArray());
                if (checkCountOfUsers()) {
                    logger.info("Count of users is same on every load");
                } else {
                    logger.info("Count of users is not same on every load");
                }
            } else {
                logger.error(TARGET_URL + " is invalid");
            }
        } catch (JsonProcessingException e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
    }

    private static boolean checkCountOfUsers() {
        HttpLoader httpLoader = HttpLoader.getInstance();
        Optional<String> firstAttemptToLoad = httpLoader.getContent(TARGET_URL);
        Optional<String> secondAttemptToLoad = httpLoader.getContent(TARGET_URL);
        if (firstAttemptToLoad.isEmpty() || secondAttemptToLoad.isEmpty()) {
            return false;
        }
        try {
            return mapper.readValue(firstAttemptToLoad.get(), User[].class).length ==
                    mapper.readValue(secondAttemptToLoad.get(), User[].class).length;
        } catch (JsonProcessingException e) {
            logger.error(e.getMessage(), e);
            return false;
        }

    }
}