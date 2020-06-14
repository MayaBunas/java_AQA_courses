package by.stqa.pft.adderssbook.tests;

import by.stqa.pft.adderssbook.model.GroupData;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Comparator;
import java.util.List;

public class GroupCreationTests extends TestBase {

  @Test
  public void testGroupCreation() throws Exception {
    app.goTo().GroupPage();
    List<GroupData> before = app.group().getList();
    GroupData group = new GroupData().withName("The Club 27")
            .withHeader("The 27 Club is a list consisting mostly of popular musicians, artists, or actors who died at age 27.")
            .wthFooter("Brian Jones, Jimi Hendrix, Janis Joplin, Jim Morrison, Kurt Cobain, Amy Winehouse.");
    app.group().create(group);
    List<GroupData> after = app.group().getList();
    Assert.assertEquals(after.size(), before.size() + 1);

    group.withId(after.stream().max((o1, o2) -> Integer.compare(o1.getId(), o2.getId())).get().getId());
    before.add(group);
    Comparator<? super GroupData> ById = (g1, g2) -> Integer.compare(g1.getId(), g2.getId());
    before.sort(ById);
    after.sort(ById);
    Assert.assertEquals(before, after);
  }
}
