package by.stqa.pft.adderssbook.tests;

import by.stqa.pft.adderssbook.model.GroupData;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Set;

public class GroupModificationTests extends TestBase {

  @BeforeMethod
  public void ensurePreconditions() {
    app.goTo().GroupPage();
    if (app.group().getSet().size() == 0) {
      app.group().create(new GroupData().withName("The Club 27")
              .withHeader("The 27 Club is a list consisting mostly of popular musicians, artists, or actors who died at age 27.")
              .wthFooter("Brian Jones, Jimi Hendrix, Janis Joplin, Jim Morrison, Kurt Cobain, Amy Winehouse."));
    }
  }

  @Test
  public void testGroupModification() {
    Set<GroupData> before = app.group().getSet();
    GroupData mofifiedGroup = before.iterator().next();
    GroupData group = new GroupData().withId(mofifiedGroup.getId()).withName("The Club 27")
            .withHeader("People still loving you, guys!");
    app.group().modify(group);
    Set<GroupData> after = app.group().getSet();
    Assert.assertEquals(after.size(), before.size());

    before.remove(mofifiedGroup);
    before.add(group);
    Assert.assertEquals(before, after);
  }
}
