import java.time.LocalDateTime;
import java.util.HashMap;

/**
 * Models a Show object used to store Show data.
 * @author Anthony Do
 *
 */
public class Show {
    private String showName;
    private LocalDateTime dateTime;
    private boolean[] mainFloorSeats;
    private boolean[] southBalconySeats;
    private boolean[] eastBalconySeats;
    private boolean[] westBalconySeats;
    private HashMap<String, boolean[]> map = new HashMap<>();
    
    /**
     * Constructs a Show object
     * @param showName The show name.
     * @param dateTime The date and time of the show.
     */
    public Show(String showName, LocalDateTime dateTime) {
        this.showName = showName;
        this.dateTime = dateTime;
        this.mainFloorSeats = new boolean[151];
        this.southBalconySeats = new boolean[51];
        this.eastBalconySeats = new boolean[101];
        this.westBalconySeats = new boolean[101];
        map.put("m", mainFloorSeats);
        map.put("sb", southBalconySeats);
        map.put("eb", eastBalconySeats);
        map.put("wb", westBalconySeats);
    }
    
    /**
     * Reserves the specified seat in the Show.
     * @param section The section.
     * @param seatNumber The seat number.
     * @return True if seat was successfully reserved, otherwise false.
     */
    public boolean reserveSeat(String section, int seatNumber) {
        boolean[] sectionArray = map.get(section);
        if (sectionArray != null && seatNumber <= sectionArray.length && seatNumber >= 0) {
            if (sectionArray[seatNumber] == false) {
                sectionArray[seatNumber] = true;
                return true;
            } else {
                return false;
            }
        } 
        return false;
    }
    
    /**
     * Cancels seat specified by section and seat number.
     * @param section The section.
     * @param seatNumber The seat number.
     */
    public void cancelSeat(String section, int seatNumber) {
        boolean[] sectionArray = map.get(section);
        if (sectionArray != null && seatNumber <= sectionArray.length && seatNumber >= 0) {
            if (sectionArray[seatNumber] == true) {
                sectionArray[seatNumber] = false;
            } 
        } 
    }
    
    public String getShowName() {
        return showName;
    }
    
    public LocalDateTime getDateTime() {
        return dateTime;
    }
    
    public boolean[] getMainFloorSeats() {
        return mainFloorSeats;
    }
    
    public boolean[] getSouthBalconySeats() {
        return southBalconySeats;
    }
    
    public boolean[] getEastBalconySeats() {
        return eastBalconySeats;
    }
    
    public boolean[] getWestBalconySeats() {
        return westBalconySeats;
    }
    
}
