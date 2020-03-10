import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * Models the User object
 * @author Anthony Do
 *
 */
public class User {
    private String userId;
    private String password;
    
    /**
     * Constructs a User object
     * @param userId the username of the User account
     * @param password the password of the User account
     */
    public User(String userId, String password) {
        this.userId = userId;
        this.password = password;
    }
    
    /**
     * Returns the userId
     * @return userId
     */
    public String getUserId() {
        return userId;
    }
    
    /**
     * Returns the password
     * @return password
     */
    public String getPassword() {
        return password;
    }
    
    /**
     * Gets the User's reservations made by calling UserManagement.getReservations()
     * @return An ArrayList of Reservation
     */
    public ArrayList<Reservation> getReservations() {
        return UserManagement.getReservations(this);
    }
    
    /**
     * Gets the User's reservations by taking a Show object.
     * @param show The show to get the User's reservations from.
     * @return The Reservation.
     */
    public Reservation getReservation(Show show) {
        ArrayList<Reservation> reservations = getReservations();
        for (Reservation reservation : reservations) {
            if (reservation.getShow().getDateTime().equals(show.getDateTime())) {
                return reservation;
            }
        }
        return null;
    }
    
    /**
     * Gets the User's reservations by taking a LocalDateTime.
     * @param datetime The date and time object to get reservations from.
     * @return An ArrayList of Reservation
     */
    public ArrayList<Reservation> getReservation(LocalDateTime datetime) {
        ArrayList<Reservation> reservations = getReservations();
        ArrayList<Reservation> requestedReservations = new ArrayList<>();
        
        for (Reservation reservation : reservations) {
            if (reservation.getShow().getDateTime().equals(datetime)) {
                requestedReservations.add(reservation);
            }
        }
        return requestedReservations;
    }
}
