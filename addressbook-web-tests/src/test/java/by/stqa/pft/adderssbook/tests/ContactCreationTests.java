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
      app.group().create(new GroupData().withName("The Club 27")
              .withHeader("The 27 Club is a list consisting mostly of popular musicians, artists, or actors who died at age 27.")
              .wthFooter("Brian Jones, Jimi Hendrix, Janis Joplin, Jim Morrison, Kurt Cobain, Amy Winehouse."));
    }
    app.goTo().HomePage();
  }

  @Test
  public void testContactCreation() throws Exception {
    List<ContactData> before = app.contact().getList();
    ContactData contact = new ContactData().withFirstName("Amy").withMiddleName("Jade").withLastName("Winehouse")
            .withNickname("Rehab").withTitle("Best British Female Artist").withCompany("Club 27")
            .withAddress("Southgate, London").withHomePhone("+44 20 7123 1234").withMobilePhone("+44 20 7777 7777")
            .withWorkPhone("+44 20 7111 1111").withFax("+44 20 7666 6666").withEmail1("amy.winehouse@club27.com")
            .withEmail2("amy@rehab.com").withEmail3("amy@winehouse.com")
            .withHomepage("https://en.wikipedia.org/wiki/Amy_Winehouse")
            .withBday("14").withBmonth("September").withByear("1983").withAday("23").withAmonth("July").withAyear("2011")
            .withGroup("The Club 27").withAddress2("Camden, London").withRealHome("Heaven").withNotes("Amy was the best!");
    app.contact().create(contact);
    List<ContactData> after = app.contact().getList();
    Assert.assertEquals(after.size(), before.size() + 1);

    contact.withId(after.stream().max((o1, o2) -> Integer.compare(o1.getId(), o2.getId())).get().getId());
    before.add(contact);
    Comparator<? super ContactData> ById = (с1, с2) -> Integer.compare(с1.getId(), с2.getId());
    before.sort(ById);
    after.sort(ById);
    Assert.assertEquals(before, after);
  }
}
