package by.stqa.pft.adderssbook.appmanager;

import by.stqa.pft.adderssbook.model.ContactData;
import by.stqa.pft.adderssbook.model.Contacts;
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
    selectByVisibleText(By.name("amonth"), contactData.getAmonth());
    type(By.name("ayear"), contactData.getAyear());
    if (creation) {
      selectByVisibleText(By.name("new_group"), contactData.getGroup());
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

  public void selectContactById(int id) {
    wd.findElement(By.cssSelector("input[value='" + id + "']")).click();
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
    List<WebElement> elements = wd.findElements(By.name("entry"));
    for (WebElement element : elements) {
      List<WebElement> cells = element.findElements(By.tagName("td"));
      if (id == elementId(cells.get(0))) {
        cells.get(7).findElement(By.tagName("a")).click();
      }
    }

    /* WebElement checkbox = wd.findElement(By.cssSelector(String.format("input[value = '%s']", id)));
    WebElement row = checkbox.findElement(By.xpath("./../.."));
    List<WebElement> cells = row.findElements(By.tagName("td"));
    cells.get(7).findElement(By.tagName("a")).click(); */

    // wd.findElement(By.xpath(String.format("//input[@value = '%s']/../../td[8]/a", id))).click();
    // wd.findElement(By.xpath(String.format("tr[.//input[@value = '%s']]/td[8]/a", id))).click();
    // wd.findElement(By.cssSelector(String.format("a[href='edit.php?id=%s']", id))).click();
  }

  public void submitContactModification() {
    click(By.xpath("(//input[@name='update'])[2]"));
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
        contactsCache.add(new ContactData()
                .withId(id).withFirstName(name).withLastName(surname).withAddress(address));
      } catch (IndexOutOfBoundsException e) {
        System.out.println("Table has been changed! Cells are not correct.");
      }
    }
    return new Contacts(contactsCache);
  }
}
