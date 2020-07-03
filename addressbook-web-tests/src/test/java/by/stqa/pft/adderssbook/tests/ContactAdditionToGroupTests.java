package by.stqa.pft.adderssbook.tests;

import by.stqa.pft.adderssbook.model.ContactData;
import by.stqa.pft.adderssbook.model.Contacts;
import by.stqa.pft.adderssbook.model.GroupData;
import by.stqa.pft.adderssbook.model.Groups;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactAdditionToGroupTests extends TestBase {

  @BeforeMethod
  public void ensurePreconditions() {
    createContactIfIfNotExists();
    createGroupIfNotExists();

    Groups dbGroups = app.db().groups();
    Contacts dbContacts = app.db().contacts();

    for (ContactData contact : dbContacts) {
      if (!contact.getGroups().equals(dbGroups)) {
        return;
      }
    }
    app.goTo().groupPage();
    app.group().create(new GroupData().withName("Family").withHeader("I love my family!").withFooter("Live long and prosper!"));
  }

  @Test
  public void testContactAdditionToGroup() {
    Groups dbGroupsBefore = app.db().groups();
    Contacts dbContactsBefore = app.db().contacts();

    ContactData contactToAdd = contactToBeAdded(dbGroupsBefore, dbContactsBefore);
    GroupData groupToAdd = groupToBeAdded(dbGroupsBefore, contactToAdd);

    app.goTo().homePage();
    app.contact().addContactToGroup(contactToAdd, groupToAdd);
    app.goTo().homePage();

    assertThat(app.contact().count(), equalTo(dbContactsBefore.size()));

    Contacts dbContactsAfter = app.db().contacts();
    assertThat(dbContactsAfter, equalTo(dbContactsBefore.without(contactToAdd).withAdded(contactToAdd.inGroup(groupToAdd))));

    verifyContactListInUI();
  }

  public ContactData contactToBeAdded(Groups groups, Contacts contacts) {
    for (ContactData contact : contacts) {
      if (!contact.getGroups().equals(groups)) {
        return contact;
      }
    }
    return null;
  }

  public GroupData groupToBeAdded(Groups groups, ContactData contact) {
    for (GroupData group : groups) {
      if (!contact.getGroups().contains(group)) {
        return group;
      }
    }
    return null;
  }
}