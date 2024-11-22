import java.util.List;

class Semaphore {
    private final int capacity;
    private int availableSpots;

    public Semaphore(int initial) {
        this.capacity = initial;
        this.availableSpots = initial;
    }
    public synchronized void P(List<String> logMessages, String name, int gateNumber, float arriveTime) {
        long waitStartTime = System.currentTimeMillis();
        while (availableSpots == 0) {
            try {
                logMessages.add("Car " + name + " from Gate " + gateNumber + " is waiting for a spot.");
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        long waitEndTime = System.currentTimeMillis();
        long waitTime = (waitEndTime - waitStartTime) / 1000;

        availableSpots--;
        if (waitTime > 0) {
            logMessages.add("Car " + name + " from Gate " + gateNumber + " parked after waiting for " + waitTime +
                    " units of time. (Parking Status: " + (capacity - availableSpots) + " spots occupied)");
        } else {
            logMessages.add("Car " + name + " from Gate " + gateNumber + " parked. (Parking Status: " +
                    (capacity - availableSpots) + " spots occupied)");
        }
    }
    public synchronized void V(List<String> logMessages, String name, int gateNumber, float departureTime) {
        availableSpots++;
        logMessages.add("Car " + name + " from Gate " + gateNumber + " left the parking spot after " +
                departureTime + " units of time. (Parking Status: " + (capacity - availableSpots) + " spots occupied)");
        notify();
    }
}
