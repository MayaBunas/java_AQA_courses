package by.stqa.pft.adderssbook.tests;

import by.stqa.pft.adderssbook.model.GroupData;
import by.stqa.pft.adderssbook.model.Groups;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

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
    Groups before = app.group().getSet();
    GroupData modifiedGroup = before.iterator().next();
    GroupData group = new GroupData().withId(modifiedGroup.getId()).withName("The Club 27")
            .withHeader("People still loving you, guys!");
    app.group().modify(group);
    Groups after = app.group().getSet();
    assertThat(after.size(), equalTo(before.size()));
    assertThat(after, equalTo(before.without(modifiedGroup).withAdded(group)));
  }
}
