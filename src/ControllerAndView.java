/**
 * - controllerAndView class - all GUI components for the calendar
 *  application and controller actions 
 * 
 * @author Steven Gonzalez SSID: 009387092
 * @copyRight - ? hasnt beem published
 * version1
 * November 20, 2015
 */
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Line2D;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ControllerAndView extends JFrame{
	
	public String[] months = {"January", "February", "March", "April","May", "June" , "July", "August", "September" , "October", "November", "December"};
	public String[] days = {"Sun", "Mon", "Tue", "Wed", "Thur", "Fri", "Sat"};
	public String[] days2 = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
	public GregorianCalendar cal;
	public int currentMM;
	public int currentDD;
	public int currentYY;
	public int offset;
	public JPanel textPanel;
	public JPanel mPanel;
	public JFrame mainFrame;
	public JPanel dayIdentifier;
	public Model model;
	public JTextField myField;
	public JPanel myJP;
	public JFrame popAdder;
	public JTextField jField;
	public JTextField fieldie1;
	public JTextField fieldie2;
	public JTextArea display;
	
	/* Constructor for the controller/view of the mvc model
	 */
	public ControllerAndView()	{
		model = new Model();
		readIn();
		cal = new GregorianCalendar();	
		currentMM = cal.get(cal.MONTH);
		currentDD = cal.get(cal.DAY_OF_MONTH);
		currentYY = cal.get(cal.YEAR);
		mainFrame = new JFrame();
		dayIdentifier = new JPanel();
		textPanel = new JPanel();
		calendar();
	}
	
	/* populates the mainframe with a calendar JPanel
	 * @return the mainframe, frame containing all the components for the calendar GUI application 
	 */
	public JFrame calendar()	{	
		mainFrame.setTitle("Hw4 - MyGuiCalendar - Steven Gonzalez - 009387092");
		mPanel = new JPanel();
		
		currentDD = (int) (cal.get(cal.DATE));
		final JPanel names = new JPanel();
		names.setLayout(new BorderLayout());
		names.add(topCalPanel(), BorderLayout.NORTH);
		names.add(dayLabels(), BorderLayout.CENTER);
		names.add(dayButtonPanel(), BorderLayout.SOUTH);
		
		JPanel buttonsPanel2 = new JPanel();
		buttonsPanel2.setLayout(new FlowLayout());
		JButton previous = new JButton(" < ");
		JButton next = new JButton(" > ");
		JButton quit = new JButton("Quit");
		JButton create = new JButton("Create");
		create.addActionListener(new ActionListener()	{
			@Override
			public void actionPerformed(ActionEvent e)	{
				adder();
				
			}	});
		buttonsPanel2.add(create, BorderLayout.WEST);
		quit.addActionListener(new ActionListener()	{
			@Override
			public void actionPerformed(ActionEvent e)	{
				exitStatus();
			}	});
		buttonsPanel2.add(quit, BorderLayout.CENTER);
		
		JPanel actionPanel = new JPanel();
		actionPanel.setLayout(new FlowLayout());
		previous.addActionListener(new ActionListener()	{
			@Override
			public void actionPerformed(ActionEvent event)	{
				dayIdentifier.removeAll();
				 mPanel.setVisible(false);
				if(currentDD == cal.getActualMinimum(cal.DAY_OF_MONTH))	{
					cal.add(cal.DATE, -1);
					currentDD = cal.get(cal.DAY_OF_MONTH);
				}
				else	{
					currentDD -= 1;
					cal.set(cal.DATE, currentDD);
				}
				dayView();
				calendar();
			}		});
		actionPanel.add(previous, BorderLayout.WEST);
		next.addActionListener(new ActionListener()	{
			@Override
			public void actionPerformed(ActionEvent e)	{
				dayIdentifier.removeAll();
				mPanel.setVisible(false);
				if(currentDD == cal.getActualMaximum(cal.DAY_OF_MONTH))	{
					cal.add(cal.DATE, 1);
					currentDD = cal.get(cal.DAY_OF_MONTH);
				}
				else	{
					currentDD += 1;	 
					cal.set(cal.DATE, currentDD);
				}
				dayView();
				calendar();
			}			});
		actionPanel.add(next, BorderLayout.CENTER);
		actionPanel.add(buttonsPanel2, BorderLayout.EAST);
		
		JPanel monthPanel = new JPanel();
		monthPanel.setLayout(new BorderLayout());
		monthPanel.add(names, BorderLayout.NORTH);
		monthPanel.add(actionPanel, BorderLayout.CENTER);
		
		mPanel.add(monthPanel, BorderLayout.NORTH);
		mainFrame.setPreferredSize(new Dimension(728, 285));
		mainFrame.add(mPanel, BorderLayout.WEST);
		mainFrame.add(seperator(), BorderLayout.CENTER);
		mainFrame.add(dayView(), BorderLayout.EAST);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.pack();
		mainFrame.setVisible(true);
		return mainFrame;
	}
	
	/* makes a JPanel with the current month and year 
	 * @return top, panel containing the current month and year 
	 */
	public JPanel topCalPanel()	{
		JPanel top = new JPanel();
		top.setLayout(new FlowLayout());
		top.add(new JLabel(months[cal.get(cal.MONTH)]));
		top.add(new JLabel(String.valueOf(cal.get(cal.YEAR))));
		return top;
	}
	
	/* makes a JPanel contain the labels for the monthView calendar 
	 * @return dayNames a panel consisting of the days of the week 
	 */
	public JPanel dayLabels()	{
		JPanel dayNames = new JPanel();
		dayNames.setLayout(new GridLayout(1, 7));
		for(int i = 0; i < days.length; i++)	{
			String dayName = days[i];
			dayNames.add(new JLabel("       " + dayName));
		}
		return dayNames;
	}
	
	/* populates the calendar panel with buttons representing the days of the current month 
	 * @return JPanel containing the month buttons 
	 */
	public JPanel dayButtonPanel()	{
		int min = cal.getActualMinimum(cal.DAY_OF_MONTH);
		int max = cal.getActualMaximum(cal.DAY_OF_MONTH)+1;
		currentDD = cal.get(cal.DATE);
		cal.set(cal.DATE, 1);
		int firstDayOfMonth = cal.get(cal.DAY_OF_WEEK)-1;
		cal.set(cal.DATE, currentDD);
		offset = firstDayOfMonth % 7;
		JPanel daysPanel = new JPanel();
		daysPanel.setLayout(new GridLayout(6, 7));
		
		for(int i = 0; i < offset; i++)	{
			JButton blank = new JButton("");
			blank.setPreferredSize(new Dimension(60, 25));
			daysPanel.add(blank);
		}

		for(int i = 1; i < (43 - offset); i++)	{
			String dayValue = "";
			if(i >= max + firstDayOfMonth - offset){
				dayValue = "";
			}
			else if(i == currentDD)	{
				dayValue = "[" + String.valueOf((i)) + "]";
			}
			else	{
				dayValue = String.valueOf((i));
			}
			
			final JButton day = new JButton(dayValue);
			day.setPreferredSize(new Dimension(60, 25));
			day.addActionListener(new ActionListener()	{
				@Override
				public void actionPerformed(ActionEvent e)	{
					dayIdentifier.removeAll();
					String btnId = e.getActionCommand();
					System.out.println(btnId);
					mPanel.setVisible(false);
					if(btnId.contains("[") | btnId.contains("]"))	{
						btnId = btnId.substring(1, btnId.length()-1);
					}
					
					int parse = Integer.parseInt(btnId);
					cal.set(cal.DATE, parse);
					currentDD = parse;
					dayView();
					calendar();
				}	});
			daysPanel.add(day);
		}
		return daysPanel;
	}
	
	/* Separator panel for the mainFrame
	 * @return line a JPanel containing of a single line to seperate dayView and monthView 
	 */
	public JPanel seperator()	{
		JPanel line = new JPanel() {
			@Override
			public void paintComponent(Graphics g)	{
				Graphics2D g2 = (Graphics2D) g;
				g2.draw(new Line2D.Double(0, 0, 0, 300));
			}		};
		line.setPreferredSize(new Dimension(1, 380));
		return line;
	}
	
	/* JPanel that defines the dayView of the calendar application 
	 * @return textPanel, a JPanel displaying the current date and any events scheduled on that date 
	 */
	public JPanel dayView()	{
		int currentDayName = cal.get(cal.DAY_OF_WEEK) - 1;
		dayIdentifier.setLayout(new BorderLayout());
		textPanel.setPreferredSize(new Dimension(280, 180));
		int month = cal.get(cal.MONTH) + 1;
		int day = cal.get(cal.DAY_OF_MONTH);
		JLabel thisDay = new JLabel(days2[currentDayName] + "  " + month + "/" + day);
		thisDay.setHorizontalAlignment(JLabel.CENTER);
		dayIdentifier.add(thisDay, BorderLayout.NORTH);
		
		display = new JTextArea();
		display.setPreferredSize(new Dimension(260, 220));
		JScrollPane scroller = new JScrollPane(display);
	    scroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
	    display.setText("");
		
	    String toGet = month + "/" + day + "/" + cal.get(cal.YEAR);
	    ArrayList<Event> temp = model.get(toGet);
		if(temp == null)	{
			display.append("No events scheduled on this day");
		}
		else	{
			for(int j = 0; j < temp.size(); j++)	{
				display.append(temp.get(j).toString2() + "\n");
		 	}
		}
		dayIdentifier.add(scroller, BorderLayout.CENTER);
		textPanel.add(dayIdentifier, BorderLayout.CENTER);
		return textPanel;
	}
	
	/* reads in a text file on program start up and adds events in the txt file to the model 
	 */
	public void readIn()	{
		String event = null;
		String date = null;
		String sTime = null;
		String eTime = null;
	
		try {
			FileReader file= new FileReader(new File("events.txt"));
			BufferedReader reader = new BufferedReader(file);
		
			String line = reader.readLine();
			while(!line.equals("end."))	{
				if(line.equals(""))	{
					line = reader.readLine();
				}
				if(line.equals("end."))	{
					break;
				}
				if(event == null)	{
					event = line;
				}
				else if(date == null)	{
					date = line;
				}
				else if(sTime == null)	{
					sTime = line;
				}
				else if(eTime == null)	{
					eTime = line;
				}
				
				if(event != null && date != null && sTime != null && eTime != null)	{
					Event newEvent = new Event(event, date, sTime, eTime);
					model.add(newEvent.getDate(), newEvent);
					event = null;
					date = null;
					sTime = null;
					eTime = null;
				}
				line = reader.readLine();
			}
		}
		catch(FileNotFoundException e)	{
			System.out.println("First run of this prgoram therefor no file yet exists");
		}
		catch(IOException e)	{
			System.out.println("IOException occured");
		}	
	}
	
	/* creates a new file called "events.txt" and writes all events in the
	 * model to the .txt file and then terminates the program
	 */
	public void exitStatus()	{
		try	{
			File file = new File("events.txt");
			
			if(!file.exists())	{
				file.createNewFile();
			}
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			
			Set<String> keys = model.keySet();
			for(String s : keys)	{
				for(int i = 0; i < model.get(s).size(); i++)	{
					String e = model.get(s).get(i).getEvent() + "\n";
					String d = model.get(s).get(i).getDate() + "\n";
					String sT = model.get(s).get(i).getStartT() + "\n";
					String eT = model.get(s).get(i).getEndT() + "\n";
					String blank = "\n";
					bw.write(e);
					bw.write(d);
					bw.write(sT);
					bw.write(eT);
					bw.write(blank);
				}
			}
			bw.write("end.");
			bw.close();
			System.exit(0);
		}
		catch(IOException e)	{
			System.out.println("Could not write events.txt");
		}
	}
	
	/* makes a pop out adder frame for the create button 
	 * adds appropriate panels and functions 
	 */
	public void adder()	{
		popAdder = new JFrame();
		popAdder.setTitle("Event Adder Frame");
		popAdder.setLayout(new BorderLayout());
		popAdder.setPreferredSize(new Dimension(300, 90));
		
		popAdder.add(namePanel(), BorderLayout.NORTH);
		popAdder.add(spacerPanel(), BorderLayout.CENTER);
		popAdder.add(datePanel(), BorderLayout.CENTER);
		popAdder.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		popAdder.pack();
		popAdder.setVisible(true);
	}
	
	/* blank panel to separate the dateField panel and the date panel 
	 * returns a JPanel with the spacer 
	 */
	public JPanel spacerPanel()	{
		JPanel spacer = new JPanel();
		spacer.setPreferredSize(new Dimension(1,1));
		return spacer;
	}
	
	/* makes a JPanel that will hold the name JTextField 
	 * @return a JPanel containing the nameField for the pop out adder 
	 */
	public JPanel namePanel()		{
		JPanel myJP = new JPanel();
		myJP.setLayout(new BorderLayout());
		
		JLabel myLabel = new JLabel("Event Name:");
		myJP.add(myLabel, BorderLayout.WEST);
		myJP.add(nameField(), BorderLayout.CENTER);
		return myJP;
	}
	
	/* makes a JTextField for the user to enter a name for the event to add 
	 * @return myField the JTextField for the event name to be added 
	 */
	public JTextField nameField()	{
		myField = new JTextField();
		myField.setPreferredSize(new Dimension(200, 25));
		return myField;
	}
	
	/* makes a JPanel for the pop out adder
	 * JPanel holes the dateField panel and time panels as well as the save button
	 * @return dPanel panel holding time panels and save button 
	 */
	public JPanel datePanel()	{
		JPanel dPanel = new JPanel();
		dPanel.setLayout(new BorderLayout());
		dPanel.setPreferredSize(new Dimension(200, 15));
		dPanel.add(dateField(), BorderLayout.WEST);
		dPanel.add(timePanel(), BorderLayout.CENTER);
		dPanel.add(saveButton(), BorderLayout.EAST);
		return dPanel;
	}
	
	/* make the date jTextField for the pop out adder
	 * return a JTextField containing the current date 
	 */
	public JTextField dateField()	{
		String month = String.valueOf(cal.get(cal.MONTH) + 1);
		String day = String.valueOf(cal.get(cal.DAY_OF_MONTH));
		String year = String.valueOf(cal.get(cal.YEAR));
		String toSet = month + "/" + day + "/" + year;
		jField = new JTextField();
		jField.setPreferredSize(new Dimension(75, 15));
		jField.setText(toSet);
		return jField;
	}
	
	/* bottom panel of pop out adder frame, defines start + end time JTextFields 
	 * @return a JPanel that holds the start and end time JTextFields 
	 */
	public JPanel timePanel()	{
		JPanel tPanel = new JPanel();
		tPanel.setLayout(new BorderLayout());
		tPanel.add(tSlot1(), BorderLayout.WEST);
		tPanel.add(new JLabel("to"), BorderLayout.CENTER);
		tPanel.add(tSlot2(), BorderLayout.EAST);
		return tPanel;
	}
	
	/* start time slot in pop out adder 
	 * @return JTextField for time slot1 
	 */
	public JTextField tSlot1()	{
		fieldie1 = new JTextField();
		fieldie1.setPreferredSize(new Dimension(63, 15));
		fieldie1.setText("10:30am");
		return fieldie1;
	}
	
	/* ending time slot in pop out adder
	 * @return JTextField for time slot2  
	 */
	public JTextField tSlot2()	{
		fieldie2 = new JTextField();
		fieldie2.setPreferredSize(new Dimension(63, 20));
		fieldie2.setText("11:30am");
		return fieldie2;
	}
	
	/* defines the functionality of the save button in the pop out adder frame 
	 * @return a JButton with the functions for the save event option 
	 */
	public JButton saveButton()	{
		JButton jb = new JButton("SAVE");
		jb.addActionListener(new ActionListener()	{
			@Override
			public void actionPerformed(ActionEvent e)	{
				String event = myField.getText();
				String date = jField.getText() ;
				String sTime = trimThisMess(fieldie1.getText());
				String eTime = trimThisMess(fieldie2.getText());
				
				Event newEvent = new Event(event, date, sTime, eTime);
				
				if(checkForConflict(newEvent) == true)	{
					model.add(date, newEvent);
					mPanel.setVisible(false);
					refreshDayView();
					calendar();
					//if(display.getText().contains("No events"))	{
					//	display.setText("");
					//	display.append(newEvent.toString2());
					//}
					//else	{
					//	display.append(newEvent.toString2() + "\n");
					//}
				}
				else	{
					final JFrame smFrame = new JFrame();
					smFrame.setLayout(new BorderLayout());
					
					JLabel conMsg = new JLabel("  Time conflict, Try again  ");
					
					smFrame.add(conMsg, BorderLayout.NORTH);
					JButton msg = new JButton("OK");
					msg.addActionListener(new ActionListener()	{
						@Override
						public void actionPerformed(ActionEvent e)	{
							smFrame.dispose();
							adder();
						}	});
					smFrame.add(spacerPanel(), BorderLayout.CENTER);
					smFrame.add(msg, BorderLayout.SOUTH);
					smFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					smFrame.pack();
					smFrame.setVisible(true);
					}
					popAdder.dispose();
					}		});
			return jb;
	}
	
	/* takes input from the pop out Adder methods and trims is so the program can identify the start and end time of an event 
	 * 
	 * @param standard the untouched string representing the initial time input entered by the user to be trimmed 
	 * @return string of the trimmed time 
	 */
	public String trimThisMess(String standard)	{
		String returnThis = "";
		String toAdd1 = "";
		String toAdd2 = "";
		String timeOfDay = "";
		
		if(standard.length() < 6)	{
			standard = 0 + "" + standard;
		}
		
		int i;
		for(i = 0; i < standard.length()-1; i++)	{
			String temp = standard.substring(i, i+1);
			if(temp.equals(":"))	{
				toAdd1 = standard.substring(0, i);
				toAdd2 = standard.substring(i+1, i + 3);
				timeOfDay = standard.substring(i+3, standard.length());
				break;
			}
		}
		
		int value = Integer.parseInt(toAdd1);
		if(timeOfDay.equals("pm") && value != 12)	{
			value = value + 12;
			toAdd1 = String.valueOf(value);
		}
		else if(timeOfDay.equals("am") && value == 12) 	{
			value = 0;
			toAdd1 = String.valueOf(value);
		}
		return toAdd1 + "" + toAdd2;
	}
	
	/* checks for a time conflict on a given day in the model 
	 * 
	 * @param e the event to be added to the model 
	 * @return conflict, boolean that checks for a time conflict 
	 */
	public boolean checkForConflict(Event e)	{
		boolean conflict = false;
		ArrayList<Event> temp = model.get(e.getDate());
		if(temp == null)	{
			conflict = true;
		}
		else	{
			
			int checkST = Integer.parseInt(e.getStartT());
			int checkET = Integer.parseInt(e.getEndT());
			
			for(int i = 0; i < temp.size(); i++)	{
				int modelST = Integer.parseInt(temp.get(i).getStartT());
				int modelET = Integer.parseInt(temp.get(i).getEndT());
				
				if(checkST >= modelST && checkST <= modelET)	{
					conflict = false;
					break;
				}
				else if(checkET >= modelST && checkET <= modelET)	{
					conflict = false;
					break;	
				}
				else if(modelST >= checkST && modelST <= checkET)	{
					conflict = false;
					break;	
				}
				else	{
					conflict = true;
				}
			}
		}
		return conflict;
	}
	
	/*
	 * refreshes the dayView panel when an event is added to the model
	 */
	public JPanel refreshDayView()	{
		dayIdentifier.removeAll();
		int currentDayName = cal.get(cal.DAY_OF_WEEK) - 1;

		int month = cal.get(cal.MONTH) + 1;
		int day = cal.get(cal.DAY_OF_MONTH);
		JLabel thisDay = new JLabel(days2[currentDayName] + "  " + month + "/" + day);
		thisDay.setHorizontalAlignment(JLabel.CENTER);
		dayIdentifier.add(thisDay, BorderLayout.NORTH);
		
		display = new JTextArea();
		display.setPreferredSize(new Dimension(260, 220));
		JScrollPane scroller = new JScrollPane(display);
	    scroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
	    display.setText("");
		
	    String toGet = month + "/" + day + "/" + cal.get(cal.YEAR);
	    ArrayList<Event> temp = model.get(toGet);
		if(temp == null)	{
			display.append("No events scheduled on this day");
		}
		else	{
			for(int j = 0; j < temp.size(); j++)	{
				display.append(temp.get(j).toString2() + "\n");
		 	}
		}
		dayIdentifier.add(scroller, BorderLayout.CENTER);
		textPanel.add(dayIdentifier, BorderLayout.CENTER);
		return textPanel;
	}
}