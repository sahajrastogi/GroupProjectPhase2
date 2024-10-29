package groupproject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;

public class HelpItem {
	//ensures no double assignment of ID
	public static int maxId = 0;
	
	//Help Item fields
	public int id;
	public String title;
	public String description;
	public String body;
	public ArrayList<String> keywords;
	public ArrayList<String> links;
	public ArrayList<String> groups;
	public String uniqueHeader;
	
	/**
	 * Default Help Item constructor
	 */
	public HelpItem() {
		id = -1;
		title = ""; description = ""; body = "";
		keywords = new ArrayList<String>();
		links = new ArrayList<String>();
		groups = new ArrayList<String>();
	}
	
	/*
	 * Generates a uniqueHeader for the HelpItem
	 */
	public void generateUniqueHeader() {
		 uniqueHeader = id + description + groups.get(0);
	}
	
	/**
	 * Debugging helper function
	 */
	public void print() {
		System.out.println("ID: " + id);
		System.out.println("Title: " + title);
		System.out.println("Description: " + description);
		System.out.println("Body: " + body);
		System.out.println("Keywords: " + listToString(keywords));
		System.out.println("Links: " + listToString(links));
		System.out.println("Groups: " + listToString(groups));
	}
	
	/**
	 * Converts a list to string for writing to a file
	 * @param a - input list
	 * @return string
	 */
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
	
	/**
	 * Converts a string to list for reading from a file
	 * @param a - input string
	 * @return list
	 */
	public static ArrayList<String> stringToList(String a) {
		if(a.equals("") || a.equals(" ")) {
			return new ArrayList<String>();
		}
		String[] list = a.split(",");
		return new ArrayList<>(Arrays.asList(list));
	}
	
	/**
	 * Converts a user inputed string a list for assignment
	 * @param a
	 * @return list
	 */
	public static ArrayList<String> prettyStringToList(String a) {
		ArrayList<String> res = new ArrayList<>(Arrays.asList(a.split(",")));
		for(int i=0;i<res.size();i++) res.set(i, res.get(i).trim());
		return res;
	}	
	
	/**
	 * Converts a list to a nicely printed string for the user to see
	 * @param a - input list
	 * @return string
	 */
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
	
	/**
	 * Adds a HelpItem to the global list
	 * @param ti - title field
	 * @param desc - description field
	 * @param bo - body field
	 * @param ke - keyword field
	 * @param li - link field
	 * @param gr - group field
	 * @return
	 */
	public static int add(String ti, String desc, String bo, String ke, String li, String gr) {
		HelpItem h = new HelpItem();
		maxId++;
		h.id = maxId;
		h.title = ti;
		h.description = desc;
		h.body = bo;
		h.keywords = prettyStringToList(ke);
		h.links = prettyStringToList(li);
		h.groups = prettyStringToList(gr);
		App.items.add(h);
		return maxId;
	}
	
	/**
	 * Backups a group of articles to a given file
	 * @param fileName - name of file
	 * @param groupName - name of group
	 * @throws Exception for file input and output
	 */
	public static void backup(String fileName, String groupName) throws Exception {
		try (FileWriter fW = new FileWriter(fileName)) {
			
			for(HelpItem h : App.items) {
				if(!h.groups.contains(groupName) && !groupName.equals("")) continue;
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
	
	/**
	 * Restores a group of help items from a given file name
	 * @param fileName - name of file
	 * @param merge - whether or not to clear or merge
	 * @throws Exception for file input and output
	 */
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
		    	maxId = Math.max(maxId,h.id);
		    }
        }
	}
	
	/**
	 * Check whether the global list contains a certain ID
	 * @param x
	 * @return boolean
	 */
	public static boolean containsID(int x) {
		for(HelpItem h : App.items) {
			if(h.id == x) return true;
		}
		return false;
	}
	
	/**
	 * Removes an element from the global list from its ID
	 * @param id
	 */
	public static void removeByID(int id) {
		for(HelpItem h : App.items) {
			if(h.id == id) {
				App.items.remove(h);
				break;
			}
		}
	}
	
	/**
	 * Returns a help item from the list from its ID
	 * @param id
	 * @return help item
	 */
	public static HelpItem itemByID(int id) {
		for(HelpItem h : App.items) {
			if(h.id == id) {
				return h;
			}
		}
		return null;
	}
	
}

