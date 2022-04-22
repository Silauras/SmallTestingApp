import entity.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;
import service.JsonLoader;
import service.database.DatabaseManager;
import service.validation.EmailValidator;
import service.validation.StringValidator;

public class Main {
    final static Logger logger = Logger.getLogger(Main.class);
    final static private StringValidator emailValidator = new EmailValidator();

    public static void main(final String[] args) {
        try {
            String json = JsonLoader.getInstance().getContent("https://jsonplaceholder.typicode.com/users").get();
            System.out.println(json);
            ObjectMapper mapper = new ObjectMapper();
            User[] users = mapper.readValue(json, User[].class);
            for (User user : users) {
                logger.info(user.getEmail() +" is " + (emailValidator.isValid(user.getEmail())? "valid": "not valid"));
            }
            DatabaseManager.getInstance().saveObjects(users);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}