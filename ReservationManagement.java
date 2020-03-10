import java.util.ArrayList;
import java.util.Arrays;

/**
 * Manages the Reservation(s) of User and Show.
 * @author Anthony Do
 *
 */
public class ReservationManagement {
    
    public static final String[] sectionNames = new String[] {
            "m", "eb", "wb", "sb"
    };
    
    /**
     * Creates a Reservation for the User and Show specified.
     * @param user The User making the reservation.
     * @param show The Show being reserved.
     * @param seats The seats being reserved.
     * @return
     */
    public static ArrayList<String> createReservation(User user, Show show, ArrayList<String> seats) {
        ArrayList<String> seatsReserved = new ArrayList<>();
        ArrayList<String> unavailableSeats = new ArrayList<>();
        
        for (int i = 0; i < seats.size(); i++) {
            String option = seats.get(i);
            
            if (Arrays.stream(sectionNames).parallel().anyMatch(option::contains)) {
                String section = option.replaceAll("\\d","");
                int number = Integer.parseInt(option.replaceAll("\\D+",""));
                
                if (show.reserveSeat(section, number)) {
                    seatsReserved.add(option);
                } else {
                    unavailableSeats.add(option);
                }
            } else {
                unavailableSeats.add(option);
            }
        }
        
        Reservation reservation = new Reservation(user.getUserId(), show, seatsReserved);
        UserManagement.addReservation(user, reservation);
        
        return unavailableSeats;
    }
    
    /**
     * Cancels reservations of the User specified.
     * @param user The User making the cancellation.
     * @param reservations The Reservation(s)
     * @param seats The seats to be canceled.
     */
    public static void cancelReservation(User user, ArrayList<Reservation> reservations, ArrayList<String> seats) {
        for (int i = 0; i < seats.size(); i++) {
            String option = seats.get(i);
            
            if (Arrays.stream(sectionNames).parallel().anyMatch(option::contains)) {
                String section = option.replaceAll("\\d","");
                int number = Integer.parseInt(option.replaceAll("\\D+",""));
                
                for (Reservation reservation : reservations) {
                    for (int j = 0; j < reservation.getSeats().size(); j++) {
                        if (reservation.getSeats().get(j).equals(option)) {
                            reservation.getShow().cancelSeat(section, number);
                            reservation.cancelSeat(option);
                        }
                    }
                }
            } 
        }
    }
}
