package by.stqa.pft.adderssbook.tests;

import by.stqa.pft.adderssbook.appmanager.ApplicationManager;
import by.stqa.pft.adderssbook.model.ContactData;
import by.stqa.pft.adderssbook.model.Contacts;
import by.stqa.pft.adderssbook.model.GroupData;
import by.stqa.pft.adderssbook.model.Groups;
import org.openqa.selenium.remote.BrowserType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class TestBase {

  Logger logger = LoggerFactory.getLogger(TestBase.class);
  protected static final ApplicationManager app = new ApplicationManager(System.getProperty("browser", BrowserType.CHROME));

  @BeforeSuite
  public void setUp() throws Exception {
    app.init();
  }

  @AfterSuite(alwaysRun = true)
  public void tearDown() throws Exception {
    app.stop();
  }

  @BeforeMethod
  public void logTestStart(Method m, Object[] p) {
    logger.info("Start test " + m.getName() + " with parameters " + Arrays.asList(p));
  }

  @AfterMethod(alwaysRun = true)
  public void logTestStop(Method m) {
    logger.info("Stop test " + m.getName());
  }

  public void verifyGroupListInUI() {
    if (Boolean.getBoolean("verifyUI")) {
      Groups dbGroups = app.db().groups();
      Groups uiGroups = app.group().getSet();
      assertThat(uiGroups, equalTo(dbGroups.stream()
              .map((g) -> new GroupData().withId(g.getId()).withName(g.getName()))
              .collect(Collectors.toSet())));
    }
  }

  public void verifyContactListInUI() {
    if (Boolean.getBoolean("verifyUI")) {
      Contacts dbContacts = app.db().contacts();
      Contacts uiContacts = app.contact().getSet();
      assertThat(uiContacts, equalTo(dbContacts.stream()
              .map((c) -> new ContactData().withId(c.getId()).withFirstName(c.getFirstName()).withLastName(c.getLastName())
                      .withAddress(c.getAddress())).collect(Collectors.toSet())));
    }
  }

  public void createContactIfIfNotExists() {
    if (app.db().contacts().size() == 0) {
      app.goTo().homePage();
      app.contact().create(new ContactData().withFirstName("Amy").withMiddleName("Jade").withLastName("Winehouse")
              .withNickname("Rehab").withTitle("Best British Female Artist").withCompany("Club 27")
              .withAddress("Southgate, London").withHomePhone("+44 20 7123 1234").withMobilePhone("+44 20 7777 7777")
              .withWorkPhone("+44 20 7111 1111").withFax("+44 20 7666 6666").withEmail1("amy.winehouse@club27.com")
              .withEmail2("amy@rehab.com").withEmail3("amy@winehouse.com")
              .withHomepage("https://en.wikipedia.org/wiki/Amy_Winehouse")
              .withBday("14").withBmonth("September").withByear("1983").withAday("23").withAmonth("July").withAyear("2011")
              .withAddress2("Camden, London").withsecondHomePhone("+ (333) 333 - 333 - 333").withNotes("Amy was the best!"));
    }
  }

  public void createGroupIfNotExists() {
    if (app.db().groups().size() == 0) {
      app.goTo().groupPage();
      app.group().create(new GroupData().withName("The Club 27")
              .withHeader("The 27 Club is a list consisting mostly of popular musicians, artists, or actors who died at age 27.")
              .withFooter("Brian Jones, Jimi Hendrix, Janis Joplin, Jim Morrison, Kurt Cobain, Amy Winehouse."));
      app.goTo().homePage();
    }
  }
}
