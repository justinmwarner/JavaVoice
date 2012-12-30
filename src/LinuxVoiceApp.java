import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

// TODO: Remove gson, and use what the voice jar uses.
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.techventus.server.voice.Voice;
import com.techventus.server.voice.datatypes.Contact;
import com.techventus.server.voice.datatypes.records.SMS;
import com.techventus.server.voice.datatypes.records.SMSThread;
import com.techventus.server.voice.util.SMSParser;

public class LinuxVoiceApp {
	public static void main(String[] args) {
		System.out.println("Start");
		StartupScreen ss = new StartupScreen();
		while (!ss.isLogin) {
		}
		System.out.println("Entered info: " + ss.toString());
		User user = ss.getInfo();// new User("bodyid@gmail.com",
									// "jw2228266637");
		Voice v = null;
		try {
			v = new Voice(user.email, user.pass);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Set voice.");
		while (true) {
			String newSMS = null;
			try {
				if (v != null) {
					SMSParser parser = new SMSParser(v.getUnreadSMS(),
							user.phone);
					System.out.println("Get unreads.");
					Collection<SMSThread> unreads = parser.getSMSThreads();
					String newMessages = "";
					for (SMSThread sms : unreads) {
						if (sms.isCall()) {

						} else {
							if (!sms.getRead()) {
								Collection<SMS> smsThread = sms.getAllSMS();
								String out = "";
								String author = "";
								for (SMS s : smsThread) {
									out += s.getContent() + "\n";
									author = s.getFrom().getName() + " "
											+ s.getFrom().getNumber();
								}
								newMessages = notifyString(author, out);
								System.out.println("Output: " + newMessages);
							}
						}
					}
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("Sleep...");
			try {
				Thread.sleep(150000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("Wake");
		}
	}

	private static String notifyString(String author, String content) {
		Tooltip tt = new Tooltip(content, author, 3000);
		/*
		 * String cmd = author + " " + content.replace("\n", "").replace("\"",
		 * ""); Notification n = new Notification("New Txt Message from " +
		 * author + "!", cmd, "Tester"); n.setTimeout(3000);
		 * n.setCategory("im"); n.connect(new Notification.Closed() {
		 * 
		 * @Override public void onClosed(Notification n) {
		 * System.out.println("You closed " + n.toString()); }
		 * 
		 * }); n.show();
		 */try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return author + " " + content;
	}

	private static ArrayList<Contact> getContacts(User user, Voice v)
			throws IOException {
		ArrayList<Contact> contacts = new ArrayList<Contact>();
		String general = v.getGeneral();
		general = general.substring(general.indexOf("var _gcData"),
				general.indexOf("_gvRun"));
		JsonParser parse = new JsonParser();
		general = general.substring(14).replace("\'", "\"").replace("};", "}");
		// System.out.println(general);
		JsonObject joGeneral = (JsonObject) parse.parse(general)
				.getAsJsonObject();
		user.phone = joGeneral.get("number").getAsJsonObject().get("formatted")
				.toString();
		JsonArray contactArray = (JsonArray) joGeneral.get("rankedContacts")
				.getAsJsonArray();
		JsonObject joContacts = (JsonObject) joGeneral.get("contacts");
		for (int i = 0; i < contactArray.size(); i++) {
			// name id number imageurl
			String name = "", phone = "", imageURL = "";
			String id = contactArray.get(i).toString();
			JsonObject joContact = joContacts.get(id.replace("\"", ""))
					.getAsJsonObject();
			name = joContact.get("name").toString();
			phone = joContact.get("phoneNumber").toString().replace("+", "");
			imageURL = joContact.get("photoUrl").toString();

			contacts.add(new Contact(name, id, phone, imageURL));
			// System.out.println(contacts.get(i));
		}
		return contacts;
	}
}
