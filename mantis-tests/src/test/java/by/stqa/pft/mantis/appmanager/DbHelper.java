package by.stqa.pft.mantis.appmanager;

import by.stqa.pft.mantis.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.List;

public class DbHelper {

  private final SessionFactory sessionFactory;

  public DbHelper() {
    // A SessionFactory is set up once for an application!
    final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure() // configures settings from hibernate.cfg.xml
            .build();
    sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
  }

  public List<User> regularUsers() {
    Session session = sessionFactory.openSession();
    session.beginTransaction();

    List<User> result = session.createQuery("from User where access_level = 25").list();

    session.getTransaction().commit();
    session.close();

    return result;
  }
}
