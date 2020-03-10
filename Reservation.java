import java.util.ArrayList;
import java.util.Arrays;

/**
 * Models a Reservation object used to store reservation data.
 * @author Anthony Do
 *
 */
public class Reservation {
    public static final int mainFloorPrice = 35;
    public static final int mainFloorPremium = 45;
    public static final int southBalconyPrice = 50;
    public static final int southBalconyPremium = 55;
    public static final int westBalconyPrice = 40;
    public static final int eastBalconyPrice = 40;
    
    public static final String[] sectionNames = new String[] {
            "m", "eb", "wb", "sb"
    };
    
    private String userId;
    private Show show;
    private ArrayList<String> seats;
    private int totalPrice;
    
    /**
     * Constructs a Reservation object
     * @param userId UserId of the User making the reservation.
     * @param show The show being reserved.
     * @param reservation The seats reserved.
     */
    public Reservation(String userId, Show show, ArrayList<String> reservation) {
        this.userId = userId;
        this.show = show;
        this.seats = reservation;
        calculatePrice();
    }
    
    /**
     * Calculates the total price of the reservation.
     */
    private void calculatePrice() {
        int price = 0;
        int day = show.getDateTime().getDayOfMonth();
        int month = show.getDateTime().getMonthValue();
        
        for (int i = 0; i < seats.size(); i++) {
            String option = seats.get(i);
            
            if (Arrays.stream(sectionNames).parallel().anyMatch(option::contains)) {
                String section = option.replaceAll("\\d","");
                int number = Integer.parseInt(option.replaceAll("\\D+",""));
                
                if (section.equals("m")) {
                    if (number <= 100) {
                        price += mainFloorPrice;
                    } else {
                        price += mainFloorPremium;
                    }
                } else if (section.equals("sb")) {
                    if (number <= 25) {
                        price += southBalconyPrice;
                    } else {
                        price += southBalconyPremium;
                    }
                } else if (section.equals("wb")) {
                    price += westBalconyPrice;
                } else if (section.equals("eb")) {
                    price += eastBalconyPrice;
                }
            }
        }
        
        if (seats.size() >= 5 && seats.size() <= 10 && !(month == 12 && (day == 26 || day == 27))) {
            price -= (2 * seats.size());
        }
        
        if (seats.size() >= 11 && seats.size() <= 20 && !(month == 12 && (day == 26 || day == 27))) {
            price -= (5 * seats.size());
        }
        
        if (month == 12 && (day == 26 || day == 27)) {
            price = seats.size() * 20;
        }
        
        totalPrice = price;
    }
    
    /**
     * Cancels seat and recalculates price.
     * @param option
     */
    public void cancelSeat(String option) {
        for (int j = 0; j < seats.size(); j++) {
            if (seats.get(j).equals(option)) {
                seats.remove(j);
            }
        }
        System.out.println("Seat " + option + " canceled.");
        calculatePrice();
    }
    
    public ArrayList<String> getSeats() {
        return seats;
    }
    
    public int getTotalPrice() {
        return totalPrice;
    }
    
    public Show getShow() {
        return show;
    }
}