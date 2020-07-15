package by.stqa.pft.mantis.tests;

import by.stqa.pft.mantis.model.MailMessage;
import by.stqa.pft.mantis.model.User;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;

import static org.testng.Assert.assertTrue;

public class RegistrationTests extends TestBase {

  @BeforeMethod
  public void startMailServer() {
    app.mail().start();
  }

  @Test
  public void testRegistration() throws IOException, MessagingException {
    long now = System.currentTimeMillis();
    User user = new User().withUsername(String.format("user%s", now)).withPassword("password")
            .withEmail(String.format("user%s@localhost", now));
    app.james().createUser(user.getUsername(), user.getPassword());
    app.registration().start(user);
    List<MailMessage> mailMessages = app.mail().waitForMail(2, 10000);
    MailMessage mailMessage = app.mail().findEmailBySubject(mailMessages, "[MantisBT] Account registration", user.getEmail());
    String confirmationLink = app.mail().findLink(mailMessage);
    //List<MailMessage> mailMessages = app.james().waitForMail(user.getUsername(), user.getPassword(), 60000);
    //String confirmationLink = app.james().findLink(mailMessages, user.getEmail());
    app.registration().finish(confirmationLink, user.getPassword());
    assertTrue(app.newSession().login(user.getUsername(), user.getPassword()));
  }

  @AfterMethod(alwaysRun = true)
  public void stopMailServer() {
    app.mail().stop();
  }
}
