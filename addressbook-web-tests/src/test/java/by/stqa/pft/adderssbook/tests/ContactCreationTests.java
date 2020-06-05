package by.stqa.pft.adderssbook.tests;

import by.stqa.pft.adderssbook.model.ContactData;
import by.stqa.pft.adderssbook.model.GroupData;
import org.testng.annotations.Test;

public class ContactCreationTests extends TestBase {

  @Test
  public void testContactCreation() throws Exception {
    app.getNavigationHelper().goToGroupPage();
    if (!app.getGroupHelper().isThereAGroup()) {
      app.getGroupHelper().createGroup(new GroupData("The Club 27",
              "The 27 Club is a list consisting mostly of popular musicians, artists, or actors who died at age 27.",
              "Brian Jones, Jimi Hendrix, Janis Joplin, Jim Morrison, Kurt Cobain, Amy Winehouse."));
    }
    app.getNavigationHelper().goToHomePage();
    app.getContactHelper().createContact(new ContactData("Amy", "Jade", "Winehouse", "Rehab",
            "Best British Female Artist", "Club 27", "Southgate, London",
            "+44 20 7123 1234", "+44 20 7777 7777", "+44 20 7111 1111", "+44 20 7666 6666",
            "amy.winehouse@club27.com", "amy@rehab.com", "amy@winehouse.com", "https://en.wikipedia.org/wiki/Amy_Winehouse",
            "14", "September", "1983", "23", "July", "2011", "The Club 27",
            "Camden, London", "Heaven", "Amy was the best!"));
  }
}
