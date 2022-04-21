import entity.Address;
import entity.Company;
import entity.GeoPosition;
import entity.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.jpa.internal.EntityManagerFactoryImpl;
import org.hibernate.service.ServiceRegistry;
import service.JsonLoader;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.spi.PersistenceUnitTransactionType;

public class Main {
    private static final EntityManager em;

    static {
        try {
            Configuration configuration = new Configuration();
            configuration.configure("hibernate.cfg.xml");
            configuration.addAnnotatedClass(User.class);
            configuration.addAnnotatedClass(Company.class);
            configuration.addAnnotatedClass(Address.class);
            configuration.addAnnotatedClass(GeoPosition.class);

            StandardServiceRegistryBuilder serviceRegistryBuilder = new StandardServiceRegistryBuilder();
            serviceRegistryBuilder.applySettings(configuration.getProperties());
            ServiceRegistry serviceRegistry =  serviceRegistryBuilder.build();

            EntityManagerFactory factory = new EntityManagerFactoryImpl(
                    PersistenceUnitTransactionType.RESOURCE_LOCAL, true, null, configuration, serviceRegistry, null);

            em = factory.createEntityManager();
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static void main(final String[] args) throws Exception {

        try {
            String json = JsonLoader.getInstance().getContent("https://jsonplaceholder.typicode.com/users").get();
            System.out.println(json);

            ObjectMapper mapper = new ObjectMapper();
            User[] users = mapper.readValue(json, User[].class);
            em.getTransaction().begin();
            for (User user : users) {
                em.persist(user);
                System.out.println(user.toString());

            }
            em.getTransaction().commit();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
    }
}