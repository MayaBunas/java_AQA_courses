package by.stqa.pft.github;


import com.google.common.collect.ImmutableMap;
import com.jcabi.github.*;
import org.testng.annotations.Test;

import java.io.IOException;

public class GithubTests {

  @Test
  public void testCommits() throws IOException {
    Github github = new RtGithub("3658723797eb1e1f47bcd38f04bd80bc9f28f119");
    RepoCommits commits = github.repos().get(new Coordinates.Simple("MayaBunas", "java_AQA_courses")).commits();
    for (RepoCommit commit : commits.iterate(new ImmutableMap.Builder<String, String>().build())) {
      System.out.println(new RepoCommit.Smart(commit).message());
    }
  }
}
