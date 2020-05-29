package by.stqa.pft.adderssbook.tests;

import org.testng.annotations.Test;

public class ContactDeletionTests extends TestBase {

  @Test
  public void testContactDeletion() throws Exception {
    app.selectContact();
    app.deleteSelectedContacts();
    app.closeAlertPopup();
    app.returnToHome();
  }
}
