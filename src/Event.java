import java.util.Comparator;

/**
 * - creates an Event object with information passed to it from either 
 * 		the reader reading a txt file or from the user manually entering 
 * 		the information. 
 * - retrieves Event information from an abject of type Event
 * 
 * @author Steven Gonzalez SSID: 009387092
 * @copyRight - ? hasnt beem published
 * version1
 * November 20, 2015
 */

public class Event{

	public String eventName;
	public String date;
	public String startTime;
	public String endTime;
	
	/* creates an even object with the provided information
	 * 
	 * @param e the event name
	 * @param d the date for the event 
	 * @param sT the start time for the event 
	 * @param eT the end time for the event 
	 */
	public Event(String e, String d, String sT,String eT)
	{
		eventName = e;
		date = d;
		startTime = sT;
		endTime = eT;
	}
	
	/* gets the event name 
	 * @return eventName the name of the event to return 
	 */
	public String getEvent()
	{
		return eventName;
	}
	
	/* gets the date that the event will be held on
	 * @return the date of the event 
	 */
	public String getDate()
	{
		return date;
	}
	
	/* gets the start time for an event 
	 * @return starttime the starting time of the event
	 */
	public String getStartT()
	{
		return startTime;
	}
	
	/* gets the ending time of an event 
	 * @return entTime the ending time of the event 
	 */
	public String getEndT()
	{
		return endTime;
	}
	
	/* compares events by time
	 */
	public static Comparator<Event> compareTime = new Comparator<Event>()
	{
		//@Override
		public int compare(Event e1, Event e2)	{
			return Integer.parseInt(e1.getStartT()) - Integer.parseInt(e2.getStartT());
		}
		
	};	
	
	/* compares 2 events by date
	 */
	public static Comparator<Event> compareDate = new Comparator<Event>()
	{
		public int compare(Event e1, Event e2)
		{
			int yOne = Integer.parseInt(e1.getDate().substring(6, 10));
			int yTwo =Integer.parseInt( e2.getDate().substring(6, 10));
			if(yOne == yTwo)
			{
				int mOne = Integer.parseInt(e1.getDate().substring(0, 2));
				int mTwo =Integer.parseInt(e2.getDate().substring(0, 2));
				
				if(mOne == mTwo)
				{
					return Integer.parseInt(e1.getDate().substring(3, 5)) - Integer.parseInt(e2.getDate().substring(3, 5));
				}
				else
				{
					return Integer.parseInt(e1.getDate().substring(0, 2)) - Integer.parseInt(e2.getDate().substring(0, 2));
				}
			}	
			else
			{
				return Integer.parseInt(e1.getDate().substring(6, 10)) - Integer.parseInt(e2.getDate().substring(6, 10));
			}
		}
	};
	
	/* returns a string representation of the event object
	 */
	public String toString()
	{
		return (eventName + " " + " " + startTime + " " + endTime  + " " + date);
	}
	
	/* returns a string representation of the event object in different order
	 */
	public String toString2()
	{
		return startTime + " - " + endTime + ", Event: " + eventName;
	}
}
