package by.stqa.pft.adderssbook.tests;

import by.stqa.pft.adderssbook.model.ContactData;
import by.stqa.pft.adderssbook.model.Contacts;
import by.stqa.pft.adderssbook.model.GroupData;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactModificationTests extends TestBase {

  @BeforeMethod
  public void ensurePreconditions() {
    app.goTo().homePage();
    if (app.contact().getSet().size() == 0) {
      app.goTo().groupPage();
      if (app.group().getSet().size() == 0) {
        app.group().create(new GroupData().withName("The Club 27")
                .withHeader("The 27 Club is a list consisting mostly of popular musicians, artists, or actors who died at age 27.")
                .wthFooter("Brian Jones, Jimi Hendrix, Janis Joplin, Jim Morrison, Kurt Cobain, Amy Winehouse."));
      }
      app.goTo().homePage();
      app.contact().create(new ContactData().withFirstName("Amy").withMiddleName("Jade").withLastName("Winehouse")
              .withNickname("Rehab").withTitle("Best British Female Artist").withCompany("Club 27")
              .withAddress("Southgate, London").withHomePhone("+44 20 7123 1234").withMobilePhone("+44 20 7777 7777")
              .withWorkPhone("+44 20 7111 1111").withFax("+44 20 7666 6666").withEmail1("amy.winehouse@club27.com")
              .withEmail2("amy@rehab.com").withEmail3("amy@winehouse.com")
              .withHomepage("https://en.wikipedia.org/wiki/Amy_Winehouse")
              .withBday("14").withBmonth("September").withByear("1983").withAday("23").withAmonth("July").withAyear("2011")
              .withGroup("The Club 27").withAddress2("Camden, London").withsecondHomePhone("+ (333) 333 - 333 - 333")
              .withNotes("Amy was the best!"));
    }
  }

  @Test
  public void testContactModification() {
    Contacts before = app.contact().getSet();
    ContactData modifiedContact = before.iterator().next();
    ContactData contact = new ContactData().withId(modifiedContact.getId()).withFirstName("Amy").withLastName("Winehouse")
            .withAddress("Southgate, London").withNotes("Amy is still the best!");
    app.contact().modify(contact);
    assertThat(app.contact().count(), equalTo(before.size()));
    Contacts after = app.contact().getSet();
    assertThat(after, equalTo(before.without(modifiedContact).withAdded(contact)));
  }
}
