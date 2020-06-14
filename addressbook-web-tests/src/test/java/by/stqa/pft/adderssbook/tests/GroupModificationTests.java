package by.stqa.pft.adderssbook.tests;

import by.stqa.pft.adderssbook.model.GroupData;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Comparator;
import java.util.List;

public class GroupModificationTests extends TestBase {

  @BeforeMethod
  public void ensurePreconditions() {
    app.goTo().GroupPage();
    if (app.group().getList().size() == 0) {
      app.group().create(new GroupData().withName("The Club 27")
              .withHeader("The 27 Club is a list consisting mostly of popular musicians, artists, or actors who died at age 27.")
              .wthFooter("Brian Jones, Jimi Hendrix, Janis Joplin, Jim Morrison, Kurt Cobain, Amy Winehouse."));
    }
  }

  @Test
  public void testGroupModification() {
    List<GroupData> before = app.group().getList();
    int index = before.size() - 1;
    GroupData group = new GroupData().withId(before.get(index).getId()).withName("The Club 27")
            .withHeader("People still loving you, guys!");
    app.group().modify(index, group);
    List<GroupData> after = app.group().getList();
    Assert.assertEquals(after.size(), before.size());

    before.remove(index);
    before.add(group);
    Comparator<? super GroupData> ById = (g1, g2) -> Integer.compare(g1.getId(), g2.getId());
    before.sort(ById);
    after.sort(ById);
    Assert.assertEquals(before, after);
  }
}
