/**
 * a class to define a model object
 * 
 * @author Steven Gonzalez SSID: 009387092
 * @copyRight - ? hasnt beem published
 * version1
 * November 20, 2015
 */
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Set;

public class Model{
	public static HashMap<String, ArrayList<Event>> map;
	public String myString;	// strings are added to the model
	public ArrayList<String> model;	// the model data structure
	public ArrayList<String> listeners;
	
	/* constructor - creates a model object
	 */
	public Model()
	{
		map = new HashMap<String, ArrayList<Event>>();
		//model = new ArrayList<String>();
		//listeners = new ArrayList<>();
	}
	
	/* adds an object of type Event to the hash map
	 * 
	 * @param d the date of the event used as a key for the hash map
	 * @param e the event to add
	 */
	public void add(String d,Event e)	{
		ArrayList<Event> value = map.get(d);
		if(value == null)
		{
			value = new ArrayList<Event>();
			map.put(d, value);
		}
		value.add(e);
		Collections.sort(value, Event.compareTime);
	}

	/* gets an event from the hashmap using the date string key 
	 * 
	 * @param dToGet is the string of the date used to retreive an event from the hash map
	 */
	public ArrayList<Event> get(String dToGet)	{
		ArrayList<Event> temp = map.get(dToGet);
		if(temp == null)
		 {
			 return null;
		 }
		else 
		{
			return temp;
		}
	}
	
	/* returns the keyset for all events currently in the HashMap
	 */
	public Set<String> keySet()	{
		return map.keySet();
	}
}