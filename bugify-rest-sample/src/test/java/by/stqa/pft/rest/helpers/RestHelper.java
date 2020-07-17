package by.stqa.pft.rest.helpers;

import by.stqa.pft.rest.model.Issue;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.jayway.restassured.RestAssured;

import java.io.IOException;
import java.util.Set;

public class RestHelper {

  private ApplicationManager app;

  public RestHelper(ApplicationManager app) {
    this.app = app;
  }

  public void init(String apiKey, String password) {
    RestAssured.authentication = RestAssured.basic(apiKey, password);
  }

  public Set<Issue> getIssues() throws IOException {
    String json = RestAssured.get(app.getProperty("rest.baseUrl") + "issues.json").asString();
    JsonElement parsed = JsonParser.parseString(json);
    JsonElement issues = parsed.getAsJsonObject().get("issues");
    return new Gson().fromJson(issues, new TypeToken<Set<Issue>>() {}.getType());
  }

  public String getIssueStatus(int issueId) throws IOException {
    String json = RestAssured.get(app.getProperty("rest.baseUrl") + "issues/" + issueId + ".json").asString();
    JsonElement parsedJson = JsonParser.parseString(json);
    JsonElement issues = parsedJson.getAsJsonObject().get("issues");
    return getStateName(issues);
  }

  public String getStateName(JsonElement issues) {
    if (issues instanceof JsonArray) {
      JsonArray issuesArray = issues.getAsJsonArray();
      JsonObject firstObject = issuesArray.get(0).getAsJsonObject();
      return firstObject.get("state_name").getAsString();
    }
    return null;
  }

  public int createIssue(Issue newIssue) throws IOException {
    String json = RestAssured.given()
            .parameter("subject", newIssue.getSubject())
            .parameter("description", newIssue.getDescription())
            .post(app.getProperty("rest.baseUrl") + "issues.json").asString();
    JsonElement parsed = JsonParser.parseString(json);
    return parsed.getAsJsonObject().get("issue_id").getAsInt();
  }
}
