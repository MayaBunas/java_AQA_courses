package by.stqa.pft.mantis.appmanager;

import by.stqa.pft.mantis.model.MailMessage;
import org.subethamail.wiser.Wiser;
import org.subethamail.wiser.WiserMessage;
import ru.lanwen.verbalregex.VerbalExpression;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class MailHelper {
  private ApplicationManager app;
  private final Wiser wiser;

  public MailHelper(ApplicationManager app) {
    this.app = app;
    wiser = new Wiser();
  }

  public List<MailMessage> waitForMail(int count, long timeout) throws MessagingException, IOException {
    long start = System.currentTimeMillis();
    while (System.currentTimeMillis() < start + timeout) {
      if (wiser.getMessages().size() >= count) {
        return wiser.getMessages().stream().map((m) -> toModelMail(m)).collect(Collectors.toList());
      }
      try {
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
    throw new Error("No mail :(");
  }

  public static MailMessage toModelMail(WiserMessage m) {
    try {
      MimeMessage mm = m.getMimeMessage();
      return new MailMessage(mm.getAllRecipients()[0].toString(), mm.getSubject(), (String) mm.getContent());
    } catch (MessagingException | IOException e) {
      e.printStackTrace();
      return null;
    }
  }

  public MailMessage findEmailBySubject(List<MailMessage> mailMessages, String subject, String email) {
    return mailMessages.stream().filter((m) -> m.to.equals(email))
            .filter((m) -> m.subject.equals(subject)).findFirst().get();
  }

  public String findLink(MailMessage mailMessage) {
    VerbalExpression regex = VerbalExpression.regex().find("http://").nonSpace().oneOrMore().build();
    return regex.getText(mailMessage.text);
  }

  public void start() {
    wiser.start();
  }

  public void stop() {
    wiser.stop();
  }
}
