package by.stqa.pft.adderssbook.appmanager;

import by.stqa.pft.adderssbook.model.ContactData;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.util.ArrayList;
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
    type(By.name("phone2"), contactData.getRealHome());
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

  public void selectContact(int index) {
    wd.findElements(By.name("selected[]")).get(index).click();
  }

  public void deleteSelectedContacts() {
    click(By.xpath("//input[@value='Delete']"));
  }

  public void closeAlertPopup() {
    wd.switchTo().alert().accept();
  }

  public void initContactModification() {
    click(By.xpath("//img[@alt='Edit']"));
  }

  public void submitContactModification() {
    click(By.xpath("(//input[@name='update'])[2]"));
  }

  public void createContact(ContactData contact) {
    goToCreateContactPage();
    fillContactForm(contact, true);
    submitContactCreation();
    returnToHomePage();
  }

  public boolean isThereAContact() {
    return isElementPresent(By.name("selected[]"));
  }

  public int getContactCount() {
    return wd.findElements(By.name("selected[]")).size();
  }

  public List<ContactData> getContactList() {
    List<ContactData> contacts = new ArrayList<ContactData>();
    List<WebElement> elements = wd.findElements(By.name("entry"));
    for (WebElement element : elements) {
      List<WebElement> cells = element.findElements(By.tagName("td"));
      try {
        int id = Integer.parseInt(cells.get(0).findElement(By.tagName("input")).getAttribute("id"));
        String surname = cells.get(1).getText();
        String name = cells.get(2).getText();
        String address = cells.get(3).getText();
        ContactData contact = new ContactData(id, name, null, surname, null,
                null, null, address, null, null, null, null, null,
                null, null, null, null, null, null, null, null, null,
                null, null, null, null);
        contacts.add(contact);
      } catch (IndexOutOfBoundsException e) {
        System.out.println("Table has been changed! Cells are not correct.");
      }
    }
    return contacts;
  }
}
