package by.stqa.pft.adderssbook.appmanager;

import by.stqa.pft.adderssbook.model.ContactData;
import by.stqa.pft.adderssbook.model.Contacts;
import by.stqa.pft.adderssbook.model.GroupData;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.util.List;

public class ContactHelper extends HelperBase {

  public ContactHelper(WebDriver wd) {
    super(wd);
  }

  public void goToCreateContactPage() {
    click(By.linkText("add new"));
  }

  public void fillContactForm(ContactData contactData, boolean creation) {
    type(By.name("firstname"), contactData.getFirstName());
    type(By.name("middlename"), contactData.getMiddleName());
    type(By.name("lastname"), contactData.getLastName());
    type(By.name("nickname"), contactData.getNickname());
    attach(By.name("photo"), contactData.getPhoto());
    type(By.name("title"), contactData.getTitle());
    type(By.name("company"), contactData.getCompany());
    type(By.name("address"), contactData.getAddress());
    type(By.name("home"), contactData.getHomePhone());
    type(By.name("mobile"), contactData.getMobilePhone());
    type(By.name("work"), contactData.getWorkPhone());
    type(By.name("fax"), contactData.getFax());
    type(By.name("email"), contactData.getEmail1());
    type(By.name("email2"), contactData.getEmail2());
    type(By.name("email3"), contactData.getEmail3());
    type(By.name("homepage"), contactData.getHomepage());
    click(By.name("bday"));
    selectByVisibleText(By.name("bday"), contactData.getBday());
    click(By.name("bmonth"));
    selectByVisibleText(By.name("bmonth"), contactData.getBmonth());
    type(By.name("byear"), contactData.getByear());
    click(By.name("aday"));
    selectByVisibleText(By.name("aday"), contactData.getAday());
    click(By.name("amonth"));
    selectByDropDownValue(By.name("amonth"), contactData.getAmonth());
    type(By.name("ayear"), contactData.getAyear());
    if (creation) {
      if (contactData.getGroups().size() > 0) {
        Assert.assertTrue(contactData.getGroups().size() == 1);
        selectByDropDownValue(By.name("new_group"), Integer.toString(contactData.getGroups().iterator().next().getId()));
      }
    } else {
      Assert.assertFalse(isElementPresent(By.name("new_group")));
    }
    type(By.name("address2"), contactData.getAddress2());
    type(By.name("phone2"), contactData.getSecondHomePhone());
    type(By.name("notes"), contactData.getNotes());
  }

  public void submitContactCreation() {
    click(By.xpath("(//input[@name='submit'])[2]"));
  }

  public void returnToHomePage() {
    if (isElementPresent(By.id("maintable"))) {
      return;
    }
    click(By.linkText("home page"));
  }

  public void returnToGroupContactsPage() {
    if (isElementPresent(By.id("maintable"))) {
      return;
    }
    click(By.cssSelector("i>a"));
  }

  public void selectContactById(int id) {
    wd.findElement(By.cssSelector(String.format("input[value = '%s']", id))).click();
  }

  public void deleteSelectedContacts() {
    click(By.xpath("//input[@value='Delete']"));
  }

  public void closeAlertPopup() {
    wd.switchTo().alert().accept();
  }

  public int elementId(WebElement element) {
    return Integer.parseInt(element.findElement(By.tagName("input")).getAttribute("id"));
  }

  public void initContactModificationById(int id) {

    WebElement checkbox = wd.findElement(By.cssSelector(String.format("input[value = '%s']", id)));
    WebElement row = checkbox.findElement(By.xpath("./../.."));
    List<WebElement> cells = row.findElements(By.tagName("td"));
    cells.get(7).findElement(By.tagName("a")).click();

    /*List<WebElement> elements = wd.findElements(By.name("entry"));
    for (WebElement element : elements) {
      List<WebElement> cells = element.findElements(By.tagName("td"));
      if (id == elementId(cells.get(0))) {
        cells.get(7).findElement(By.tagName("a")).click();
      }
    }*/

    // wd.findElement(By.xpath(String.format("//input[@value = '%s']/../../td[8]/a", id))).click();
    // wd.findElement(By.xpath(String.format("tr[.//input[@value = '%s']]/td[8]/a", id))).click();
    // wd.findElement(By.cssSelector(String.format("a[href='edit.php?id=%s']", id))).click();
  }

  public void submitContactModification() {
    click(By.xpath("(//input[@name='update'])[2]"));
  }

  public void addToGroup() {
    click(By.xpath("//input[@name='add']"));
  }

  public void removeFromGroup() {
    click(By.xpath("//input[@name='remove']"));
  }

  public void selectForContactGroupById(int id) {
    selectByDropDownValue(By.name("to_group"), Integer.toString(id));
  }

  public void selectGroupPageById(int id) {
    selectByDropDownValue(By.name("group"), Integer.toString(id));
  }

  public void selectAllGroupsPage() {
    selectByVisibleText(By.name("group"), "[all]");
  }

  public void create(ContactData contact) {
    goToCreateContactPage();
    fillContactForm(contact, true);
    submitContactCreation();
    contactsCache = null;
    returnToHomePage();
  }

  public void modify(ContactData contact) {
    initContactModificationById(contact.getId());
    fillContactForm(contact, false);
    submitContactModification();
    contactsCache = null;
    returnToHomePage();
  }

  public void delete(ContactData contact) {
    selectContactById(contact.getId());
    deleteSelectedContacts();
    closeAlertPopup();
    contactsCache = null;
  }

  public void addContactToGroup(ContactData contact, GroupData group) {
    selectContactById(contact.getId());
    selectForContactGroupById(group.getId());
    addToGroup();
  }

  public void deleteContactFromGroup(ContactData contact, GroupData group) {
    selectGroupPageById(group.getId());
    selectContactById(contact.getId());
    removeFromGroup();
    returnToGroupContactsPage();
    selectAllGroupsPage();
  }

  public int count() {
    return wd.findElements(By.name("selected[]")).size();
  }

  private Contacts contactsCache = null;

  public Contacts getSet() {
    if (contactsCache != null) {
      return new Contacts(contactsCache);
    }

    contactsCache = new Contacts();
    List<WebElement> elements = wd.findElements(By.name("entry"));
    for (WebElement element : elements) {
      List<WebElement> cells = element.findElements(By.tagName("td"));
      try {
        int id = elementId(cells.get(0));
        String surname = cells.get(1).getText();
        String name = cells.get(2).getText();
        String address = cells.get(3).getText();
        String allEmails = cells.get(4).getText();
        String allPhones = cells.get(5).getText();
        contactsCache.add(new ContactData()
                .withId(id).withFirstName(name).withLastName(surname).withAddress(address)
                .withAllEmails(allEmails).withAllPhones(allPhones));
      } catch (IndexOutOfBoundsException e) {
        System.out.println("Table has been changed! Cells are not correct.");
      }
    }
    return new Contacts(contactsCache);
  }

  public ContactData infoFromEditForm(ContactData contact) {
    initContactModificationById(contact.getId());
    String name = wd.findElement(By.name("firstname")).getAttribute("value");
    String surname = wd.findElement(By.name("lastname")).getAttribute("value");
    String address = wd.findElement(By.name("address")).getAttribute("value");
    String email1 = wd.findElement(By.name("email")).getAttribute("value");
    String email2 = wd.findElement(By.name("email2")).getAttribute("value");
    String email3 = wd.findElement(By.name("email3")).getAttribute("value");
    String home = wd.findElement(By.name("home")).getAttribute("value");
    String mobile = wd.findElement(By.name("mobile")).getAttribute("value");
    String work = wd.findElement(By.name("work")).getAttribute("value");
    String secondHome = wd.findElement(By.name("phone2")).getAttribute("value");
    wd.navigate().back();
    return new ContactData().withId(contact.getId()).withFirstName(name).withLastName(surname).withAddress(address)
            .withEmail1(email1).withEmail2(email2).withEmail3(email3)
            .withHomePhone(home).withMobilePhone(mobile).withWorkPhone(work).withsecondHomePhone(secondHome);
  }
}
