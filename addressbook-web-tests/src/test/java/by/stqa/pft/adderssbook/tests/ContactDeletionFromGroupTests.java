package by.stqa.pft.adderssbook.tests;

import by.stqa.pft.adderssbook.model.ContactData;
import by.stqa.pft.adderssbook.model.Contacts;
import by.stqa.pft.adderssbook.model.GroupData;
import by.stqa.pft.adderssbook.model.Groups;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactDeletionFromGroupTests extends TestBase {

  @BeforeMethod
  public void ensurePreconditions() {
    createContactIfIfNotExists();
    createGroupIfNotExists();

    Groups dbGroups = app.db().groups();
    Contacts dbContacts = app.db().contacts();
    for (ContactData contact : dbContacts) {
      if (contact.getGroups().size() != 0) {
        for (GroupData group : contact.getGroups()) {
          if (dbGroups.contains(group)) {
            return;
          }
        }
      }
    }
    app.goTo().homePage();
    app.contact().addContactToGroup(dbContacts.iterator().next(), dbGroups.iterator().next());
  }

  @Test
  public void testContactDeletionFromGroup() {
    Groups dbGroupsBefore = app.db().groups();
    Contacts dbContactsBefore = app.db().contacts();

    ContactData contactToDelete = contactToBeDeleted(dbGroupsBefore, dbContactsBefore);
    GroupData groupToDelete = groupToBeDeleted(contactToDelete);

    app.goTo().homePage();
    app.contact().deleteContactFromGroup(contactToDelete, groupToDelete);

    assertThat(app.contact().count(), equalTo(dbContactsBefore.size()));

    Contacts dbContactsAfter = app.db().contacts();
    assertThat(dbContactsAfter,
            equalTo(dbContactsBefore.without(contactToDelete).withAdded(contactToDelete.withoutGroup(groupToDelete))));

    verifyContactListInUI();
  }

  public ContactData contactToBeDeleted(Groups groups, Contacts contacts) {
    for (ContactData contact : contacts) {
      if (contact.getGroups().size() != 0) {
        for (GroupData group : contact.getGroups()) {
          if (groups.contains(group)) {
            return contact;
          }
        }
      }
    }
    return null;
  }

  public GroupData groupToBeDeleted(ContactData contact) {
    return contact.getGroups().iterator().next();
  }
}
