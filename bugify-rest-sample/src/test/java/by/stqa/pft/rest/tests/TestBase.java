package by.stqa.pft.rest.tests;

import by.stqa.pft.rest.helpers.ApplicationManager;
import org.testng.annotations.BeforeSuite;

import java.io.IOException;

public class TestBase {

  protected static final ApplicationManager app = new ApplicationManager();

  @BeforeSuite(alwaysRun = true)
  public void setUp() throws IOException {
    app.init();
    app.rest().init(app.getProperty("rest.apikey"), app.getProperty("rest.password"));
  }


  /*public boolean isIssueOpen(int issueId) {
    String status = app.rest().issueStatus(issueId);
    List<String> readyForQa = Arrays.asList("resolved", "closed");
    return !readyForQa.contains(status);
  }

  public void skipIfNotFixed(int issueId) {
    if (isIssueOpen(issueId)) {
      throw new SkipException("Ignored because of issue " + issueId);
    }
  }*/
}
