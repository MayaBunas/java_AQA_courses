package by.stqa.pft.rest.tests;

import by.stqa.pft.rest.helpers.ApplicationManager;
import org.testng.SkipException;
import org.testng.annotations.BeforeSuite;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class TestBase {

  protected static final ApplicationManager app = new ApplicationManager();

  @BeforeSuite(alwaysRun = true)
  public void setUp() throws IOException {
    app.init();
    app.rest().init(app.getProperty("rest.apikey"), app.getProperty("rest.password"));
  }


  public boolean isIssueOpen(int issueId) throws IOException {
    String status = app.rest().getIssueStatus(issueId);
    if (status != null) {
      List<String> readyForQa = Arrays.asList("Resolved", "Closed");
      return !readyForQa.contains(status);
    }
    return true;
  }

  public void skipIfNotFixed(int issueId) throws IOException {
    if (isIssueOpen(issueId)) {
      throw new SkipException("Ignored because of issue " + issueId);
    }
  }
}
