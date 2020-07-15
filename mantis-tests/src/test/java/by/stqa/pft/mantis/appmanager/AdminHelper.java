package by.stqa.pft.mantis.appmanager;

import by.stqa.pft.mantis.model.User;
import org.openqa.selenium.By;

public class AdminHelper extends HelperBase {

  public AdminHelper(ApplicationManager app) {
    super(app);
  }

  public void login() {
    wd.get(app.getProperty("web.baseUrl") + "/login_page.php");
    type(By.name("username"), app.getProperty("web.adminLogin"));
    click(By.cssSelector("input[value='Login']"));
    type(By.name("password"), app.getProperty("web.adminPassword"));
    click(By.cssSelector("input[value='Login']"));
  }

  public void startManageUser(User user) {
    click(By.xpath("//div[@id='sidebar']/ul/li[6]/a/i"));
    click(By.linkText("Manage Users"));
    //type(By.name("search"), user.getUsername());
    //click(By.cssSelector("input[value='Apply Filter']"));
    click(By.linkText(String.format("%s", user.getUsername())));
  }

  public void resetPassword() {
    click(By.cssSelector("input[value='Reset Password']"));
  }

  public void resetPasswordForUser(User user) {
    login();
    startManageUser(user);
    resetPassword();
  }
}
