package by.stqa.pft.adderssbook;

import org.testng.annotations.Test;

public class ContactDeletionTests extends TestBase {

  @Test
  public void testContactDeletion() throws Exception {
    selectContact();
    deleteSelectedContacts();
    closeAlertPopup();
    returnToHome();
  }
}
