package mate.academy;

import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

public class TicketBookingSystem {
    private final Semaphore semaphore;
    private final AtomicInteger totalSeats;

    public TicketBookingSystem(int totalSeats) {
        this.totalSeats = new AtomicInteger(totalSeats);
        this.semaphore = new Semaphore(totalSeats);
    }

    public BookingResult attemptBooking(String user) {
        BookingResult bookingResult;
        try {
            semaphore.acquire();
            if (totalSeats.getAndDecrement() > 0) {
                bookingResult = new BookingResult(user, true, "Booking successful.");
            } else {
                bookingResult = new BookingResult(user, false, "No seats available.");
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            semaphore.release();
        }
        return bookingResult;
    }
}
