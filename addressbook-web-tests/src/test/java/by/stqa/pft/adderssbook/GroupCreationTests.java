package by.stqa.pft.adderssbook;

import org.testng.annotations.Test;

public class GroupCreationTests extends TestBase {

  @Test
  public void testGroupCreation() throws Exception {
    goToGroupPage();
    initGroupCreation();
    fillGroupForm(new GroupData("Family", "Our family photo.", "I love my family!"));
    submitGroupCreation();
    returnToGroupPage();
  }
}
