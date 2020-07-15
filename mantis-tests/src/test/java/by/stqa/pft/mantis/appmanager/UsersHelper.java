package by.stqa.pft.mantis.appmanager;

import org.openqa.selenium.By;

public class UsersHelper extends HelperBase {

  public UsersHelper(ApplicationManager app) {
    super(app);
  }

  public void login(String username, String password) {
    wd.get(app.getProperty("web.baseUrl") + "/login_page.php");
    type(By.name("username"), username);
    click(By.cssSelector("input[value='Login']"));
    type(By.name("password"), password);
    click(By.cssSelector("input[value='Login']"));
  }

  public void resetPassword(String resetLink, String password) {
    wd.get(resetLink);
    type(By.name("password"), password);
    type(By.name("password_confirm"), password);
    click(By.cssSelector("span.submit-button button"));
  }
}
