package service.database;

import entity.Address;
import entity.Company;
import entity.GeoPosition;
import entity.User;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.engine.jdbc.connections.spi.ConnectionProvider;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class DatabaseManagerTest {


    private final DatabaseManager databaseManager = new DatabaseManager(
            "jdbc:h2:mem:test",
            "sa",
            "",
            "org.h2.Driver"
    );



    private User user = new User();

    {
        user.setId(1);
        user.setName("name");
        user.setUsername("username");
        user.setEmail("email@email.com");
        user.setPhone("+380123456789");
        user.setWebsite("www.website.org");
        user.setAddress(new Address(
                "street",
                "suite",
                "city",
                "012345",
                new GeoPosition(
                        new BigDecimal("0"),
                        new BigDecimal("0"))));
        user.setCompany(new Company("name", "catchPhrase", "business Strategy"));

    }

    @BeforeAll
    public static void startH2Database() throws SQLException, InterruptedException {
        StandardServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(hibernateProperties_h2())
                .build();
        Connection connection = serviceRegistry
                .getService(ConnectionProvider.class)
                .getConnection();
        Runnable runnable = () -> {
            try {
                org.h2.tools.Server.startWebServer(connection);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        };
        new Thread(runnable).start();
        Thread.sleep(2000);
    }

    private static Properties hibernateProperties_h2(){
        Properties hProps = new Properties();
        hProps.setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
        hProps.setProperty("hibernate.connection.driver_class", "org.h2.Driver");
        hProps.setProperty("hibernate.connection.url", "jdbc:h2:mem:test");
        hProps.setProperty("hibernate.connection.username", "sa");
        hProps.setProperty("hibernate.connection.password", "");
        return hProps;
    }

    @Test
    public void databaseSaveObjects() {
        databaseManager.saveObjects(new User[]{user});
        EntityManager entityManager = databaseManager.getEntityManager();
        User userFromDb = entityManager.find(User.class, 1);
        assertNotNull(userFromDb);
        assertEquals(userFromDb.getId(), 1);
        assertEquals(user, userFromDb);
    }


}
