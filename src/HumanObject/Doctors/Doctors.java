package HumanObject.Doctors;

import DataObject.Appointment.AppointmentList;
import HumanObject.BasePerson;
import HumanObject.ROLE;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * The Doctors class represents a doctor in the system, with functionality for managing appointments,
 * availability, and schedules. Doctors have ongoing, completed, and pending appointments,
 * as well as a weekly schedule of available time slots.
 */
public class Doctors extends BasePerson {
    private static int lastID = 0;

    // Lists to track different statuses of appointments for the doctor
    private AppointmentList Ongoing;
    private AppointmentList Completed;
    private AppointmentList Pending;

    // Availability map for each day, where each Boolean array represents availability for specific time slots
    private HashMap<Date, Boolean[]> availability;

    /**
     * Constructor for initializing a Doctor object from a data file.
     *
     * @param ID          The doctor's unique identifier.
     * @param Name        The doctor's name.
     * @param DOB         The doctor's date of birth.
     * @param Gender      The doctor's gender.
     * @param dateHashMap Availability schedule for specific dates.
     */
    public Doctors(int ID, String Name, Date DOB, Boolean Gender, HashMap<Date, Boolean[]> dateHashMap) {
        super(ID, Name, DOB, Gender);
        this.role = ROLE.DOCTOR;
        this.availability = dateHashMap;
        this.Ongoing = new AppointmentList(true);
        this.Completed = new AppointmentList(false);
        this.Pending = new AppointmentList(true);
    }

    /**
     * Constructor for creating a new Doctor object with a unique ID.
     *
     * @param Name   The doctor's name.
     * @param DOB    The doctor's date of birth.
     * @param Gender The doctor's gender.
     */
    public Doctors(String Name, Date DOB, Boolean Gender) {
        super(lastID++, Name, DOB, Gender);
        this.role = ROLE.DOCTOR;
        this.availability = new HashMap<>();
        this.Ongoing = new AppointmentList(true);
        this.Completed = new AppointmentList(false);
        this.Pending = new AppointmentList(true);
    }

    /**
     * Creates a time slot array for the specified date, initializing all slots to unavailable (false).
     *
     * @param date The date to add to the availability map with initial unavailability.
     */
    public void createTimeSlot(Date date) {
        Boolean[] timeSlot = new Boolean[] {false, false, false, false, false};
        availability.put(date, timeSlot);
    }

    /**
     * Marks a specific time slot on a given date as available.
     *
     * @param date The date to set the availability.
     * @param time The index of the time slot to mark as available.
     */
    public void addTimeSlot(Date date, int time) {
        Boolean[] timeSlot = availability.get(date);
        if (timeSlot == null) createTimeSlot(date);
        availability.get(date)[time] = true;
    }

    /**
     * Retrieves the availability for a given date.
     *
     * @param date The date to check availability.
     * @return A Boolean array representing time slots for the date, or null if no availability is set.
     */
    public Boolean[] getTimeSlot(Date date) {
        if (availability.containsKey(date)) return availability.get(date);
        return null;
    }

    /**
     * Marks a specific time slot as unavailable if it is currently available.
     *
     * @param date The date of the appointment.
     * @param time The index of the time slot to be removed.
     * @return True if the time slot was successfully removed, otherwise false.
     */
    public boolean removeTimeSlot(Date date, int time) {
        Boolean[] availableTimes = availability.get(date);
        if (availableTimes != null) {
            if (availableTimes[time]) {
                availableTimes[time] = false;
                return true; // Successfully booked
            }
        }
        return false; // Time not available
    }

    /**
     * Prints the unavailability schedule for the doctor for the first week from the current date.
     * Each day shows specific unavailable time slots.
     */
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
                    if (availability.get(date)[i]) {
                        switch (i) {
                            case 0 -> System.out.println("10AM-11AM");
                            case 1 -> System.out.println("11AM-12PM");
                            case 2 -> System.out.println("1PM-2PM");
                            case 3 -> System.out.println("2PM-3PM");
                            case 4 -> System.out.println("3PM-4PM");
                            default -> {}
                        }
                    }
                }
            }
        }
    }

    // Static methods to manage unique ID
    public static void setLastID(int ID) {
        lastID = ID;
    }

    public static int getLastID() {
        return lastID;
    }

    // Getters for appointment lists
    public AppointmentList getOngoingApt() {
        return Ongoing;
    }

    public AppointmentList getCompletedApt() {
        return Completed;
    }

    public AppointmentList getPendingApt() {
        return Pending;
    }

    // Getter for the availability map
    public HashMap<Date, Boolean[]> getAvailability() {
        return availability;
    }
}





