import entity.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;
import service.JsonLoader;
import service.database.DatabaseManager;
import service.validation.EmailValidator;
import service.validation.StringValidator;

import java.util.*;

public class Main {
    final static Logger logger = Logger.getLogger(Main.class);
    final static private StringValidator emailValidator = new EmailValidator();
    final static ObjectMapper mapper = new ObjectMapper();
    final static private String TARGET_URL = "https://jsonplaceholder.typicode.com/users";

    public static void main(final String[] args) {
        try {
            Optional<String> json = JsonLoader.getInstance().getContent(TARGET_URL);
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
                DatabaseManager.getInstance().saveObjects(userList.toArray());
                if (checkCountOfUsers()){
                    logger.info("Count of users is same on every load");
                }else{
                    logger.info("Count of users is not same on every load");
                }
            }else {
                logger.error(TARGET_URL + " is invalid");
            }
        } catch (JsonProcessingException e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
    }

    private static boolean checkCountOfUsers(){
        JsonLoader jsonLoader = JsonLoader.getInstance();
        Optional<String> firstAttemptToLoad = jsonLoader.getContent(TARGET_URL);
        Optional<String> secondAttemptToLoad = jsonLoader.getContent(TARGET_URL);
        if (firstAttemptToLoad.isEmpty() || secondAttemptToLoad.isEmpty()){
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