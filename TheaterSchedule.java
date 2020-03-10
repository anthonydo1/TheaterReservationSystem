import java.time.LocalDateTime;
import java.util.HashMap;

/**
 * Models a Theater Schedule object used to store and manage the schedule of all shows.
 * @author Anthony Do
 *
 */
public class TheaterSchedule {
    private HashMap<LocalDateTime, Show> schedules;
    
    /**
     * Constructs a TheaterSchedule
     */
    public TheaterSchedule() {
        schedules = new HashMap<LocalDateTime, Show>();
    }
    
    /**
     * Adds Show to the Theater Schedule.
     * @param schedule The Show to be added.
     */
    public void addSchedule(Show schedule) {
        schedules.put(schedule.getDateTime(), schedule);
    }
    
    /**
     * Retrieves schedule given date and time.
     * @param year
     * @param month
     * @param day
     * @param hour
     * @param minute
     * @return Returns a Show object of the date given.
     */
    public Show getSchedule(int year, int month, int day, int hour, int minute) {
        return schedules.get(LocalDateTime.of(year, month, day, hour, minute));
    }
    
    /**
     * Verifies if date specified has a scheduled Show.
     * @param year
     * @param month
     * @param day
     * @param hour
     * @param minute
     * @return True if Show is scheduled on that date, otherwise false.
     */
    public boolean verifyDate(int year, int month, int day, int hour, int minute) {
        LocalDateTime datetime = LocalDateTime.of(year, month, day, hour, minute);
        if (schedules.get(datetime) != null) {
            return true;
        }
        return false;
    }
}
