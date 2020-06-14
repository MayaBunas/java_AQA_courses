package by.stqa.pft.adderssbook.tests;

import by.stqa.pft.adderssbook.model.ContactData;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

public class ContactDeletionTests extends TestBase {

  @BeforeMethod
  public void ensurePreconditions() {
    app.goTo().HomePage();
    if (app.contact().getList().size() == 0) {
      app.contact().create(new ContactData().withFirstName("Amy").withLastName("Winehouse"));
    }
  }

  @Test
  public void testContactDeletion() throws Exception {
    List<ContactData> before = app.contact().getList();
    int index = before.size() - 1;
    app.contact().delete(index);
    app.goTo().HomePage();
    List<ContactData> after = app.contact().getList();
    Assert.assertEquals(after.size(), before.size() - 1);

    before.remove(index);
    Assert.assertEquals(before, after);
  }
}
