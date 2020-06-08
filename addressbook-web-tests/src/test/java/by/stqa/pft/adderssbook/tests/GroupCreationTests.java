package by.stqa.pft.adderssbook.tests;

import by.stqa.pft.adderssbook.model.GroupData;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class GroupCreationTests extends TestBase {

  @Test
  public void testGroupCreation() throws Exception {
    app.getNavigationHelper().goToGroupPage();
    List<GroupData> before = app.getGroupHelper().getGroupList();
    app.getGroupHelper().createGroup(new GroupData("The Club 27",
            "The 27 Club is a list consisting mostly of popular musicians, artists, or actors who died at age 27.",
            "Brian Jones, Jimi Hendrix, Janis Joplin, Jim Morrison, Kurt Cobain, Amy Winehouse."));
    List<GroupData> after = app.getGroupHelper().getGroupList();
    Assert.assertEquals(after.size(), before.size() + 1);
  }
}
