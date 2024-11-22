import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(4);
        List<Car> cars = new ArrayList<>();
        int car1 = 0, car2 = 0, car3 = 0;
        List<String> logMessages = Collections.synchronizedList(new ArrayList<>());
        try (BufferedReader reader = new BufferedReader(new FileReader("/Users/bdallhsydbdallh/IdeaProjects/Car_simulation/out/production/Car_simulation/input.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(", ");
                int gateNumber = Integer.parseInt(parts[0].split(" ")[1]);
                String carName = parts[1].split(" ")[1];
                float arriveTime = Float.parseFloat(parts[2].split(" ")[1]);
                float parksTime = Float.parseFloat(parts[3].split(" ")[1]);
                Car car = new Car(gateNumber, carName, arriveTime, parksTime, semaphore, logMessages);
                cars.add(car);
                if (gateNumber == 1) {
                    car1++;
                }
                else if (gateNumber == 2) {
                    car2++;
                }
                else{
                    car3++;
                }
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
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("/Users/bdallhsydbdallh/IdeaProjects/Car_simulation/out/production/Car_simulation/output.txt"))) {
            for (String message : logMessages) {
                writer.write(message);
                writer.newLine();
            }
            writer.write("Total Cars Served: " + cars.size());
            writer.newLine();
            writer.write("Details:");
            writer.newLine();
            writer.write("- Gate 1 served "+ car1 +" cars.");
            writer.newLine();
            writer.write("- Gate 2 served "+ car2 +" cars.");
            writer.newLine();
            writer.write("- Gate 3 served "+ car3 +" cars.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}