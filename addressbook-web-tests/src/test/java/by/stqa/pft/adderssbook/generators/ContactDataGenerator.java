package by.stqa.pft.adderssbook.generators;

import by.stqa.pft.adderssbook.model.ContactData;
import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.thoughtworks.xstream.XStream;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public class ContactDataGenerator {

  @Parameter(names = "-c", description = "Group Count")
  public int count;

  @Parameter(names = "-f", description = "Target file")
  public String file;

  @Parameter(names = "-d", description = "Data format")
  public String format;

  public static void main(String[] args) throws IOException {
    ContactDataGenerator generator = new ContactDataGenerator();
    new JCommander(generator).parse(args);
    generator.run();
  }

  private void run() throws IOException {
    List<ContactData> contacts = generateContacts(count);
    if (format.equals("json")) {
      saveAsJson(contacts, new File(file));
    } else if (format.equals("xml")) {
      saveAsXml(contacts, new File(file));
    } else if (format.equals("csv")) {
      saveAsCsv(contacts, new File(file));
    } else {
      System.out.println("Unrecognized format " + format);
    }
  }

  private void saveAsJson(List<ContactData> contacts, File file) throws IOException {
    Gson gson = new GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().create();
    String json = gson.toJson(contacts);
    Writer writer = new FileWriter(file);
    writer.write(json);
    writer.close();
  }

  private void saveAsXml(List<ContactData> contacts, File file) throws IOException {
    XStream xstream = new XStream();
    xstream.processAnnotations(ContactData.class);
    String xml = xstream.toXML(contacts);
    Writer writer = new FileWriter(file);
    writer.write(xml);
    writer.close();
  }

  private void saveAsCsv(List<ContactData> contacts, File file) throws IOException {
    Writer writer = new FileWriter(file);
    for (ContactData contact : contacts) {
      writer.write(String.format("%s;%s;%s;%s;%s;%s;%s;%s;%s;%s;%s;%s;%s;%s;%s;%s;%s;%s;%s;%s;%s;%s;%s;%s\n", contact.getFirstName(),
              contact.getMiddleName(), contact.getLastName(), contact.getNickname(), contact.getTitle(),
              contact.getCompany(), contact.getAddress(), contact.getHomePhone(), contact.getMobilePhone(),
              contact.getWorkPhone(), contact.getFax(), contact.getEmail1(), contact.getEmail2(), contact.getEmail3(),
              contact.getBday(), contact.getBmonth(), contact.getByear(), contact.getAday(), contact.getAmonth(), contact.getAyear(),
              contact.getHomepage(), contact.getAddress2(), contact.getSecondHomePhone(), contact.getNotes()));
    }
    writer.close();
  }

  private List<ContactData> generateContacts(int count) {
    String[] months = new String[]{"January", "February", "March", "April", "May", "June", "July", "August", "September",
            "October", "November", "December"};
    List<ContactData> contacts = new ArrayList<ContactData>();
    for (int i = 0; i < count; i++) {
      contacts.add(new ContactData().withFirstName(String.format("Amy, version %s", i))
              .withMiddleName(String.format("Jade, version %s", i))
              .withLastName(String.format("Winehouse, version %s", i))
              .withNickname(String.format("Rehab, version %s", i))
              .withTitle(String.format("Version %s: Best British Female Artist.", i))
              .withCompany(String.format("Version %s: Club 27.", i))
              .withAddress(String.format("Version %s: Southgate, London.", i))
              .withHomePhone(String.format("+44 20 7123 1234 -%s", i))
              .withMobilePhone(String.format("+44 20 7777 7777 -%s: ", i))
              .withWorkPhone(String.format("+44 20 7111 1111 -%s: ", i))
              .withFax(String.format("+44 20 7666 6666 -%s: ", i))
              .withEmail1(String.format("amy.winehouse+%s@club27.com", i))
              .withEmail2(String.format("amy+%s@rehab.com", i))
              .withEmail3(String.format("amy+%s@winehouse.com", i))
              .withHomepage(String.format("Version %s: https://en.wikipedia.org/wiki/Amy_Winehouse", i))
              .withBday(String.format("%s", i % 31 +1))
              .withBmonth(String.format("%s", months[i % 12]))
              .withByear(String.format("%s", 1980 + i))
              .withAday(String.format("%s", i % 31 +1))
              .withAmonth(String.format("%s", months[11 - i % 12]))
              .withAyear(String.format("%s", 1980 + i))
              .withAddress2(String.format("Version %s: Camden, London.", i))
              .withsecondHomePhone(String.format("+ (333) 333 - 333 - 333 -%s", i))
              .withNotes(String.format("Version %s: Amy was the best!", i)));
    }
    return contacts;
  }
}
