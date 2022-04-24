package service.database;

import entity.Address;
import entity.Company;
import entity.GeoPosition;
import entity.User;
import org.apache.log4j.Logger;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.jpa.internal.EntityManagerFactoryImpl;
import org.hibernate.service.ServiceRegistry;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.spi.PersistenceUnitTransactionType;

public class DatabaseManager {

    final Logger logger = Logger.getLogger(DatabaseManager.class);

    private final EntityManager entityManager;

    public DatabaseManager(String url, String user, String password, String driver){
        try {
            Configuration configuration = new Configuration();
            configuration.setProperty("hibernate.connection.url", url);
            configuration.setProperty("hibernate.connection.username", user);
            configuration.setProperty("hibernate.connection.password", password);
            configuration.setProperty("connection.driver_class", driver);
            configuration.setProperty("hibernate.hbm2ddl.auto", "create");
            configuration.addAnnotatedClass(User.class);
            configuration.addAnnotatedClass(Company.class);
            configuration.addAnnotatedClass(Address.class);
            configuration.addAnnotatedClass(GeoPosition.class);

            StandardServiceRegistryBuilder serviceRegistryBuilder = new StandardServiceRegistryBuilder();
            serviceRegistryBuilder.applySettings(configuration.getProperties());
            ServiceRegistry serviceRegistry = serviceRegistryBuilder.build();

            EntityManagerFactory factory = new EntityManagerFactoryImpl(
                    PersistenceUnitTransactionType.RESOURCE_LOCAL,
                    true,
                    null,
                    configuration,
                    serviceRegistry,
                    null);

            entityManager = factory.createEntityManager();
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    public void saveObjects(Object[] objects) {
        entityManager.getTransaction().begin();
        for (Object object : objects) {
            entityManager.persist(object);
            logger.info("add " + object.getClass().getName() + " to database " + object);
        }
        entityManager.getTransaction().commit();
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }
}
