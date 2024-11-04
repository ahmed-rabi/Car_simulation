import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(4);
        List<Car> cars = new ArrayList<>();
        List<String> logMessages = Collections.synchronizedList(new ArrayList<>());
        try (BufferedReader reader = new BufferedReader(new FileReader("C:\\Users\\Ahmed Rabie\\IdeaProjects\\Car_simulation\\src\\input.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(", ");
                int gateNumber = Integer.parseInt(parts[0].split(" ")[1]);
                String carName = parts[1].split(" ")[1];
                float arriveTime = Float.parseFloat(parts[2].split(" ")[1]);
                float parksTime = Float.parseFloat(parts[3].split(" ")[1]);
                Car car = new Car(gateNumber, carName, arriveTime, parksTime, semaphore, logMessages);
                cars.add(car);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<Thread> threads = new ArrayList<>();
        for (Car car : cars) {
            Thread thread = new Thread(car);
            threads.add(thread);
            thread.start();
        }
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("C:\\Users\\Ahmed Rabie\\IdeaProjects\\Car_simulation\\src\\output.txt"))) {
            for (String message : logMessages) {
                writer.write(message);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}