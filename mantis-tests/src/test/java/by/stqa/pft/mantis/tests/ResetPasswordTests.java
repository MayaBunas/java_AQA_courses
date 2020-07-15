package by.stqa.pft.mantis.tests;

import by.stqa.pft.mantis.appmanager.HttpSession;
import by.stqa.pft.mantis.model.MailMessage;
import by.stqa.pft.mantis.model.User;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;

import static org.testng.Assert.assertTrue;

public class ResetPasswordTests extends TestBase {

  @BeforeMethod
  public void ensureUserExists() throws IOException, MessagingException {
    app.mail().start();
    if (app.db().regularUsers().size() == 0) {
      long now = System.currentTimeMillis();
      User user = new User().withUsername(String.format("user%s", now)).withPassword("password")
              .withEmail(String.format("user%s@localhost", now));
      app.registration().start(user);
      List<MailMessage> mailMessages = app.mail().waitForMail(2, 10000);
      MailMessage mailMessage = app.mail().findEmailBySubject(mailMessages, "[MantisBT] Account registration", user.getEmail());
      String confirmationLink = app.mail().findLink(mailMessage);
      app.registration().finish(confirmationLink, user.getPassword());
    }
  }

  @Test
  public void testResetPassword() throws IOException, MessagingException {
    User user = app.db().regularUsers().iterator().next();

    app.admin().resetPasswordForUser(user);

    List<MailMessage> mailMessages = app.mail().waitForMail(1, 10000);
    MailMessage mailMessage = app.mail().findEmailBySubject(mailMessages, "[MantisBT] Password Reset", user.getEmail());
    String resetLink = app.mail().findLink(mailMessage);
    app.users().resetPassword(resetLink, "qwerty");

    HttpSession session = app.newSession();
    assertTrue(session.login(user.getUsername(), "qwerty"));
    assertTrue(session.isLoggedInAs(user.getUsername()));

  }

  @AfterMethod(alwaysRun = true)
  public void stopMailServer() {
    app.mail().stop();
  }
}
