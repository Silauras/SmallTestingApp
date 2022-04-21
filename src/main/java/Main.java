import entity.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import service.JsonLoader;
import service.database.DatabaseManager;

public class Main {
    public static void main(final String[] args) {
        try {
            String json = JsonLoader.getInstance().getContent("https://jsonplaceholder.typicode.com/users").get();
            System.out.println(json);
            ObjectMapper mapper = new ObjectMapper();
            User[] users = mapper.readValue(json, User[].class);
            DatabaseManager.getInstance().saveObjects(users);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}