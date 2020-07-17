package by.stqa.pft.rest.tests;

import by.stqa.pft.rest.model.Issue;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Set;

import static org.testng.Assert.assertEquals;

public class IssueTests extends TestBase{

  @Test
  public void testCreateIssue() throws IOException {
    Set<Issue> oldIssues = app.rest().getIssues();
    Issue newIssue = new Issue().withSubject("Test issue").withDescription("New test issue");
    int issueId = app.rest().createIssue(newIssue);
    Set<Issue> newIssues = app.rest().getIssues();
    oldIssues.add(newIssue.withId(issueId));
    assertEquals(newIssues, oldIssues);
  }
}
