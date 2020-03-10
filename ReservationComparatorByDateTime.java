import java.util.Comparator;

public class ReservationComparatorByDateTime implements Comparator<Reservation> {
    public int compare(Reservation reservation1, Reservation reservation2) {
        return reservation1.getShow().getDateTime().compareTo(reservation2.getShow().getDateTime());
    }
}
