package HumanObject.Doctors;

import DataObject.Appointment.AppointmentList;
import HumanObject.BasePerson;
import HumanObject.ROLE;


import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Doctors extends BasePerson {
    private static int lastID = 0;

    private AppointmentList Ongoing;
    private AppointmentList Completed;
    private AppointmentList Pending;
    private HashMap<Date, Boolean[]>availability;


    //This constructor is used for Initialising from TXT File
    public Doctors(int ID, String Name, Date DOB, Boolean Gender, HashMap<Date, Boolean[]> dateHashMap) {
        super(ID, Name, DOB, Gender);
        this.role = ROLE.DOCTOR;
        this.availability = dateHashMap;
        this.Ongoing = new AppointmentList(true);
        this.Completed = new AppointmentList(false);
        this.Pending = new AppointmentList(true);


    }
    //This constructor is for adding a Doctor into TXT file
    public Doctors(String Name, Date DOB, Boolean Gender) {
        super(lastID++, Name, DOB, Gender);
        this.role = ROLE.DOCTOR;
        this.availability = new HashMap<>();
        this.Ongoing = new AppointmentList(true);
        this.Completed = new AppointmentList(false);
        this.Pending = new AppointmentList(true);
    }

    public void createTimeSlot(Date date) {
        Boolean[] timeSlot = new Boolean[] {false,false,false,false,false};
        availability.put(date, timeSlot);
    }
    public  void addTimeSlot(Date date, int time){
        Boolean[] timeSlot = availability.get(date);
        if (timeSlot == null) createTimeSlot(date);
        availability.get(date)[time] = true;
    }


    // Check available times for a specific date
    public Boolean[] getTimeSlot(Date date) {
        if (availability.containsKey(date)) return availability.get(date);
        return null;
        //return availability.getOrDefault(date, new Boolean[5]); this will never return null and thus will not work with your code in DoctorUI
    }

    // Book an appointment and remove the booked time
    public boolean removeTimeSlot(Date date, int time) {
        Boolean[] availableTimes = availability.get(date);
        if(availableTimes != null) {
            for (int i = 0; i < 5; i++) {
                if (availableTimes[i]){
                    availableTimes[i] = false;
                    return true;//successfully booked
                }
            }
        }
         return false;// Time not available
        }




    public void printFirstWeekTimeSlot() {
        Calendar calendar = Calendar.getInstance();
        Date today = calendar.getTime();

        // Set end of the week (6 days after today)
        calendar.add(Calendar.DAY_OF_YEAR, 6);
        Date endOfWeek = calendar.getTime();

        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");

        System.out.println("Unavailability for the first week:");
        for (Map.Entry<Date, Boolean[]> entry : availability.entrySet()) {
            Date date = entry.getKey();
            if (date.compareTo(today) >= 0 && date.compareTo(endOfWeek) <= 0) {
                System.out.println("Date: " + dateFormatter.format(date));
                for (int i = 0; i < 5; i++) {
                    if (availability.get(date)[i]){
                        switch (i){
                            case 0:
                                System.out.println("10AM-11AM");
                                break;
                            case 1:
                                System.out.println("11AM-12PM");
                                break;
                            case 2:
                                System.out.println("1PM-2PM");
                                break;
                            case 3:
                                System.out.println("2PM-3PM");
                                break;
                            case 4:
                                System.out.println("3PM-4PM");
                                break;
                            default:
                                break;
                        }
                    }
                }

            }
        }
    }



    public static void setLastID(int ID) {
        lastID = ID;
    }

    public static int getLastID() {
        return lastID;
    }

    public AppointmentList getOngoingApt() {
        return Ongoing;
    }

    public AppointmentList getCompletedApt() {
        return Completed;
    }

    public AppointmentList getPendingApt() {
        return Pending;
    }

    public HashMap<Date, Boolean[]> getAvailability() {
        return availability;
    }
}





