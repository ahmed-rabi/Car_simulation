import java.util.List;

class Car implements Runnable {
    int gateNumber;
    String name;
    float arriveTime;
    float departureTime;
    Semaphore sem;
    List<String> logMessages;
    public Car(int gateNumber, String name, float arriveTime, float departureTime, Semaphore sem, List<String> logMessages) {
        this.gateNumber = gateNumber;
        this.name = name;
        this.arriveTime = arriveTime;
        this.departureTime = departureTime;
        this.sem = sem;
        this.logMessages = logMessages;
    }
    @Override
    public void run() {
        try {
            Thread.sleep((long) (this.arriveTime * 1000));
            log("Car " + name + " from Gate " + gateNumber + " arrived at time " + arriveTime);
            sem.P(logMessages, name, gateNumber, arriveTime);
            Thread.sleep((long) (this.departureTime * 1000));
            sem.V(logMessages, name, gateNumber, departureTime);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    private void log(String message) {
        logMessages.add(message);
    }
}
