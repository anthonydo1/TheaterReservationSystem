import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Manages User login, sign-up, reservations, and data.
 * @author Anthony Do
 *
 */
public class UserManagement {
    private static HashMap<String, User> userDatabase = new HashMap<>();
    private static HashMap<User, ArrayList<Reservation>> userInfo = new HashMap<>();
    
    /**
     * Verifies if userId exists in HashMap and checks if password is correct. Used for login authorization purposes.
     * @param userId The userId specified during login.
     * @param password The password specified during login.
     * @return A User object, null if userId does not exist.
     */
    public static User verifyLogin(String userId, String password) {
        if (userDatabase.containsKey(userId) && userDatabase.get(userId).getPassword().equals(password)) {
            return userDatabase.get(userId);
        }
        return null;
    }
    
    /**
     * Used for sign up purposes. Checks if userId has been taken.
     * @param userId The userId specified during sign-up.
     * @param password The password specified during sign-up.
     * @return A User object, null if there are userId collisions/invalid password.
     */
    public static User signup(String userId, String password) {
        if (password == null || password.equals(""))
            return null;
        if (userDatabase.put(userId, new User(userId, password)) == null) {
            return userDatabase.get(userId);
        }
        return null;
    }

    /**
     * Links Reservation to User.
     * @param user The User making the reservation.
     * @param reservation The Reservation to be linked.
     */
    public static void addReservation(User user, Reservation reservation) {
        if (userInfo.get(user) == null) {
            ArrayList<Reservation> newReservation = new ArrayList<Reservation>();
            newReservation.add(reservation);
            userInfo.put(user, newReservation);
        } else {
            userInfo.get(user).add(reservation);
        }
    }

    /**
     * Retrieves the Reservations of the User specified.
     * @param user User making the request.
     * @return An ArrayList of Reservation(s) made by the User.
     */
    public static ArrayList<Reservation> getReservations(User user) {
        if (userInfo.containsKey(user)) {
            return userInfo.get(user);
        }
        return null;
    }

    /**
     * Saves all reservation data.
     */
    public static void saveData() {
        try {
            FileWriter writer = new FileWriter("reservations.txt");
            for (User key : userInfo.keySet()) {
                ArrayList<Reservation> reservations = userInfo.get(key);
                for (Reservation reservation : reservations) {
                    writer.write(reservation.getShow().getDateTime() + " Seats Reserved: "
                            + reservation.getSeats().toString() + ", User ID:  " + key.getUserId());
                }
            }
            writer.close();
        } catch (Exception e) {

        }
    }
}
