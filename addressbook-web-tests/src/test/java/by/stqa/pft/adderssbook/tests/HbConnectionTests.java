package by.stqa.pft.adderssbook.tests;

import by.stqa.pft.adderssbook.model.ContactData;
import by.stqa.pft.adderssbook.model.GroupData;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

public class HbConnectionTests {

  private SessionFactory sessionFactory;

  @BeforeClass
  protected void setUp() throws Exception {
    // A SessionFactory is set up once for an application!
    final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure() // configures settings from hibernate.cfg.xml
            .build();
    try {
      sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
    } catch (Exception e) {
      e.printStackTrace();
      // The registry would be destroyed by the SessionFactory, but we had trouble building the SessionFactory
      // so destroy it manually.
      StandardServiceRegistryBuilder.destroy(registry);
    }
  }

  @Test
  public void testHbConnection() {
    Session session = sessionFactory.openSession();
    session.beginTransaction();


    List<GroupData> resultGroups = session.createQuery("from GroupData").list();
    for (GroupData group : resultGroups) {
      System.out.println(group);
    }
    System.out.println(resultGroups.size());


    List<ContactData> resultContacts = session.createQuery("from ContactData where deprecated = '0000-00-00'").list();
    for (ContactData contact : resultContacts) {
      System.out.println(contact);
    }
    System.out.println(resultContacts.size());


    session.getTransaction().commit();
    session.close();
  }
}
