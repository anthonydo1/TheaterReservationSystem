import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

/**
 * Manages the user input and prompts.
 * @author Anthony Do
 *
 */
public class ReservationSystem {
    public static Scanner scanner = new Scanner(System.in);
    public static final String showName = "Miracle on 34th Street";
    public static final LocalDate start = LocalDate.of(2020, Month.DECEMBER, 23);
    public static final LocalDate end = LocalDate.of(2021, Month.JANUARY, 2);
    public static final LocalTime[] times = {LocalTime.of(18, 30), LocalTime.of(20, 30)};
    public static final int mainFloorCapacity = 150;
    public static final int southBalconyCapacity = 50;
    public static final int eastBalconyCapacity = 100;
    public static final int westBalconyCapacity = 100;
    public static final String[] sectionNames = new String[] {
            "m", "eb", "wb", "sb"
    };
    
    private User currentUser;
    private TheaterSchedule schedules;
    
    /**
     * Initializes the reservation system.
     */
    public void start() {
        schedules = new TheaterSchedule();
        LocalDate current = start;
        
        for (int i = 0; i <= ChronoUnit.DAYS.between(start, end); i++) {
            for (int j = 0; j < times.length; j++) {
                Show schedule = new Show(showName, LocalDateTime.of(current.plusDays(i), times[j]));
                schedules.addSchedule(schedule);
            }
        }
        initialMenu();
    }
    
    
    /**
     * Initial menu prompt
     */
    public void initialMenu() {
        System.out.println("Sign [U]p   Sign [I]n   E[X]it");
        String option = scanner.next();
        
        if (option.equalsIgnoreCase("U")) {
            promptSignUp();
        } else if (option.equalsIgnoreCase("I")) {
            promptLogin();
        } else if (option.equalsIgnoreCase("X")) {
            promptExit();
        } else {
            System.out.println("Invalid option, please select one of the options.");
            initialMenu();
        }
    }
    
    /**
     * Transaction mode prompt
     */
    public void promptTransactionMode() {
        System.out.println("\n[R]eserve    [V]iew    [C]ancel    [O]ut");
        String option = scanner.next();
        
        if (option.equalsIgnoreCase("R")) {
            promptReserve();
        } else if (option.equalsIgnoreCase("V")) {
            promptView();
        } else if (option.equalsIgnoreCase("C")) {
            promptCancel();
        } else if (option.equalsIgnoreCase("O")) {
            promptOut();
        } else {
            System.out.println("Invalid option, please select one of the options.");
            promptTransactionMode();
        }
    }
    
    /**
     * Sign up prompt
     */
    public void promptSignUp() {
        System.out.println("<--------- Sign Up --------->");
        
        System.out.print("Enter username: ");
        String userId = scanner.next();
        
        System.out.print("Enter password: ");
        String password = scanner.next();
        
        User user = UserManagement.signup(userId, password);
        if (user == null) {
            System.out.println("Username has been taken or password has to be at least one character.");
            promptSignUp();
        } else {
            System.out.println("Sign up successful, welcome " + user.getUserId() + "!");
            currentUser = user;
            promptTransactionMode();
        }
    }
    
    /**
     * Login prompt
     */
    public void promptLogin() {
        System.out.println("<--------- Login --------->");
        
        System.out.print("Enter username: ");
        String userId = scanner.next();
        
        System.out.print("Enter password: ");
        String password = scanner.next();
        
        User user = UserManagement.verifyLogin(userId, password);
        if (user == null) {
            System.out.println("User/Password is invalid.");
            promptSignUp();
        } else {
            System.out.println("Login successful, welcome " + user.getUserId() + "!");
            currentUser = user;
            promptTransactionMode();
        }
    }
    
    /**
     * Exit prompt
     */
    public void promptExit() {
        UserManagement.saveData(); // Save data on exit.
    }
    
    /**
     * Reservation prompt
     */
    public void promptReserve() {
        System.out.println("<--------- Schedule --------->");
        System.out.println("Select showings from: " + start.toString() + " to " + end.toString());
        
        System.out.print("Enter month: ");
        int month = scanner.nextInt();
        System.out.print("Enter day: ");
        int day = scanner.nextInt();
        System.out.print("Enter year: ");
        int year = scanner.nextInt();
        
        System.out.println("Select time of show: ");
        for (int i = 0; i < times.length; i++) {
            System.out.println(" [" + i + "] " + times[i].getHour() + ":" + times[i].getMinute());
        }
        int time = scanner.nextInt();
        int hour = times[time].getHour();
        int minute = times[time].getMinute();
        
        if (schedules.verifyDate(year, month, day, hour, minute) == true) {
            promptShowSeating(year, month, day, hour, minute);
        } else {
            System.out.println("Date/time does not exist.");
            promptReserve();
        }
    }
    
    /**
     * Shows the seating for the date entered.
     * @param year
     * @param month
     * @param day
     * @param hour
     * @param minute
     */
    public void promptShowSeating(int year, int month, int day, int hour, int minute) {
        Show show = schedules.getSchedule(year, month, day, hour, minute);
        
        System.out.println("<-------------------- Seating -------------------->");
        
        System.out.print("Seats available: \n");
        boolean[] mainFloor = show.getMainFloorSeats();
        boolean[] eastBalcony = show.getEastBalconySeats();
        boolean[] westBalcony = show.getWestBalconySeats();
        boolean[] southBalcony = show.getSouthBalconySeats();
        
        System.out.println("<----- Main Floor (m1 - m150) ----->");
        printSeating(mainFloor);
        System.out.println("\n\n<----- East Balcony (eb1 - eb25) ----->");
        printSeating(eastBalcony);
        System.out.println("\n\n<----- West Balcony (wb1 - wb100) ----->");
        printSeating(westBalcony);
        System.out.println("\n\n<----- South Balcony (sb1 - sb50) ----->");
        printSeating(southBalcony);
        System.out.println("\n<------------------------->");
        
        ArrayList<String> seatsChosen = new ArrayList<>();
        boolean finished = false;
        
        System.out.println("\nSelect seat by entering section code + number, enter [Y] to confirm reservation."); 
        
        
        while (finished == false) {
            String option = scanner.next();
            if (option.equalsIgnoreCase("Y")) { // Checks if option is a confirmation.
                // Creates reservation for the User
                ArrayList<String> unavailableSeats = ReservationManagement.createReservation(currentUser, show, seatsChosen); 
                finished = true;
                if (unavailableSeats.size() > 0) { // If the createReservation() call returns a nonempty array, print out the unavailable seats.
                    System.out.print("Could not reserve the following seats: ");
                    for (String str : unavailableSeats) {
                        System.out.print(str + " ");
                    }
                } else {
                    System.out.println("Successfully reserved all seats.");
                    int price = currentUser.getReservation(show).getTotalPrice();
                    System.out.println("Total price: $" + price);
                }
            } else if (Arrays.stream(sectionNames).parallel().anyMatch(option::contains)) { // Checks if section prefix is an actual section
                seatsChosen.add(option);
            } else {
                System.out.println(option + " is an invalid option, please select again.");
            }
        }
        
        promptTransactionMode(); // After reservation for seats is done, go back to transaction menu.
    }
    
    /**
     * Prints the seating for each section.
     * @param seating The array of seats.
     */
    private void printSeating(boolean[] seating) {
        for (int i = 1; i < seating.length; i++) {
            if (i % 20 == 0) System.out.print("\n");
            if (seating[i] == false) {
                System.out.print(i + " ");
            }
        }
    }
    
    /**
     * Displays all reservations made by the current user.
     */
    public void promptView() {
        System.out.println("<----- View ----->");
        ArrayList<Reservation> reservations = currentUser.getReservations();
        Collections.sort(reservations, new ReservationComparatorByDateTime()); // Sort reservations by date and time
        
        for (Reservation reservation : reservations) {
            System.out.println(reservation.getShow().getDateTime().toString() + ", " + reservation.getShow().getShowName() 
                    + ", Seats: " + reservation.getSeats().toString() + ", " + "Total Price: $" + reservation.getTotalPrice());
        }
        
        promptTransactionMode();
    }
    
    /**
     * Cancellation prompt
     */
    public void promptCancel() {
        System.out.print("Enter month: ");
        int month = scanner.nextInt();
        System.out.print("Enter day: ");
        int day = scanner.nextInt();
        System.out.print("Enter year: ");
        int year = scanner.nextInt();
        
        System.out.println("Select time of show: ");
        for (int i = 0; i < times.length; i++) {
            System.out.println(" [" + i + "] " + times[i].getHour() + ":" + times[i].getMinute());
        }
        int time = scanner.nextInt();
        int hour = times[time].getHour();
        int minute = times[time].getMinute();
        
        // Get reservation(s) of current user by date and time.
        ArrayList<Reservation> reservations = currentUser.getReservation(LocalDateTime.of(year, month, day, hour, minute)); 
        
        // Print reservations
        for (Reservation reservation : reservations) {
            System.out.println(reservation.getShow().getDateTime() + " Seats Reserved: " + reservation.getSeats().toString());
        }
        
        ArrayList<String> seats = new ArrayList<String>();
        boolean finished = false;
        
        System.out.println("Select seat by entering section code + number, enter [C] to confirm cancellation.");
        while (finished == false) {
            String option = scanner.next();
            if (option.equalsIgnoreCase("C")) { // Confirm cancellation option
                finished = true;
                ReservationManagement.cancelReservation(currentUser, reservations, seats); // Cancel reservation
            } else {
                seats.add(option); // Add seat to cancel list
            }
        }
        
        promptTransactionMode();
    }
    
    /**
     * Sign out prompt.
     */
    public void promptOut() {
        if (currentUser.getReservations() == null) {
            initialMenu();
        } else {
            System.out.println("<----- Receipt ----->");
            ArrayList<Reservation> reservations = currentUser.getReservations();
            Collections.sort(reservations, new ReservationComparatorByDateTime());
            
            for (Reservation reservation : reservations) {
                System.out.println(reservation.getShow().getDateTime().toString() + ", " + reservation.getShow().getShowName() 
                        + ", Seats: " + reservation.getSeats().toString() + ", " + "Total Price: $" + reservation.getTotalPrice());
            }
            
            initialMenu();
        }
    }
    
    public static void main(String[] args) {
        ReservationSystem test = new ReservationSystem();
        test.start();
    }
}
