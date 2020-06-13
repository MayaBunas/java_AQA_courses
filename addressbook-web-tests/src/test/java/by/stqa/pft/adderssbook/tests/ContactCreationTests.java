package by.stqa.pft.adderssbook.tests;

import by.stqa.pft.adderssbook.model.ContactData;
import by.stqa.pft.adderssbook.model.GroupData;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Comparator;
import java.util.List;

public class ContactCreationTests extends TestBase {

  @BeforeMethod
  public void ensurePreconditions() {
    app.goTo().GroupPage();
    if (app.group().getList().size() == 0) {
      app.group().create(new GroupData("The Club 27",
              "The 27 Club is a list consisting mostly of popular musicians, artists, or actors who died at age 27.",
              "Brian Jones, Jimi Hendrix, Janis Joplin, Jim Morrison, Kurt Cobain, Amy Winehouse."));
    }
    app.goTo().HomePage();
  }

  @Test
  public void testContactCreation() throws Exception {
    List<ContactData> before = app.contact().getList();
    ContactData contact = new ContactData("Amy", "Jade", "Winehouse", "Rehab",
            "Best British Female Artist", "Club 27", "Southgate, London",
            "+44 20 7123 1234", "+44 20 7777 7777", "+44 20 7111 1111", "+44 20 7666 6666",
            "amy.winehouse@club27.com", "amy@rehab.com", "amy@winehouse.com", "https://en.wikipedia.org/wiki/Amy_Winehouse",
            "14", "September", "1983", "23", "July", "2011", "The Club 27",
            "Camden, London", "Heaven", "Amy was the best!");
    app.contact().create(contact);
    List<ContactData> after = app.contact().getList();
    Assert.assertEquals(after.size(), before.size() + 1);

    contact.setId(after.stream().max((o1, o2) -> Integer.compare(o1.getId(), o2.getId())).get().getId());
    before.add(contact);
    Comparator<? super ContactData> ById = (с1, с2) -> Integer.compare(с1.getId(), с2.getId());
    before.sort(ById);
    after.sort(ById);
    Assert.assertEquals(before, after);
  }
}
