import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

class Semaphore {
    private final int capacity;
    private AtomicInteger availableSpots;
    public Semaphore(int initial) {
        this.capacity = initial;
        this.availableSpots = new AtomicInteger(initial);
    }
    public synchronized void P(List<String> logMessages, String name, int gateNumber) {
        while (availableSpots.get() == 0) {
            try {
                logMessages.add("Car " + name + " from Gate " + gateNumber + " is waiting for a spot.");
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        availableSpots.decrementAndGet();
        logMessages.add("Car " + name + " from Gate " + gateNumber + " parked. (Parking Status: " + (capacity - availableSpots.get()) + " spots occupied)");
    }
    public synchronized void V(List<String> logMessages, String name, int gateNumber) {
        availableSpots.incrementAndGet();
        logMessages.add("Car " + name + " from Gate " + gateNumber + " left the parking spot. (Parking Status: " + (capacity - availableSpots.get()) + " spots occupied)");
        notify();
    }
}