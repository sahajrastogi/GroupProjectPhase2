package groupproject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;

public class HelpItem {
	public int id;
	public String title;
	public String description;
	public String body;
	public ArrayList<String> keywords;
	public ArrayList<String> links;
	public ArrayList<String> groups;
	
	public HelpItem() {
		id = -1;
		title = ""; description = ""; body = "";
		keywords = new ArrayList<String>();
		links = new ArrayList<String>();
		groups = new ArrayList<String>();
	}
	
	public void print() {
		System.out.println("ID: " + id);
		System.out.println("Title: " + title);
		System.out.println("Description: " + description);
		System.out.println("Body: " + body);
		System.out.println("Keywords: " + listToString(keywords));
		System.out.println("Links: " + listToString(links));
		System.out.println("Groups: " + listToString(groups));
	}
	public static String listToString(ArrayList<String> a) {
		String res = "";
		for (String s : a) {
			if(s.equals("") || s.equals(" ")) continue;
			res +=s;
			res +=',';
		}
		if (res.equals("")) return res;
		return res.substring(0,res.length()-1);
	}
	
	public static String listToStringPretty(ArrayList<String> a) {
		String res = "";
		for (String s : a) {
			if(s.equals("") || s.equals(" ")) continue;
			res +=s;
			res +=", ";
		}
		if (res.length() < 2) return res;
		return res.substring(0,res.length()-2);
	}
	
	public static ArrayList<String> stringToList(String a) {
		if(a.equals("") || a.equals(" ")) {
			return new ArrayList<String>();
		}
		String[] list = a.split(",");
		return new ArrayList<>(Arrays.asList(list));
	}
	
	public static void backup(String fileName, String groupName) throws Exception {
		try (FileWriter fW = new FileWriter(fileName)) {
			
			for(HelpItem h : App.items) {
				if(!h.groups.contains(groupName) && !groupName.equals("all")) continue;
				fW.write(Integer.toString(h.id)); fW.write("\n");
				fW.write(h.title); fW.write("\n");
				fW.write(h.description); fW.write("\n");
				fW.write(h.body); fW.write("\n");
				fW.write(listToString(h.keywords));fW.write("\n");
				fW.write(listToString(h.links));fW.write("\n");
				fW.write(listToString(h.groups));fW.write("\n");
			}
		}
	}
	
	public static void restore(String fileName, boolean merge) throws Exception {
        try(BufferedReader br = new BufferedReader( new FileReader(fileName))){
        	if(!merge) {
        		App.items.clear();
        	}
        	//finish loop
        	String line;
		    while ((line = br.readLine()) != null) {
		    	HelpItem h = new HelpItem();
		    	h.id = Integer.parseInt(line); line = br.readLine();
		    	h.title = line;  line = br.readLine();
		    	h.description = line;  line = br.readLine();
		    	h.body = line;  line = br.readLine();
		    	h.keywords = stringToList(line);  line = br.readLine();
		    	h.links = stringToList(line);  line = br.readLine();
		    	h.groups = stringToList(line);
		    	
		    	if(!containsID(h.id)) App.items.add(h);
		    }
        }
	}
	
	public static boolean containsID(int x) {
		for(HelpItem h : App.items) {
			if(h.id == x) return true;
		}
		return false;
	}
	
	public static void removeByID(int id) {
		for(HelpItem h : App.items) {
			if(h.id == id) {
				App.items.remove(h);
				break;
			}
		}
	}
	
}

