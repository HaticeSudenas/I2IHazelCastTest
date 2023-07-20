package org.example;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        // Step 1: Generate 20 random numbers and store them in HazelCast
        List<Integer> randomNumbers = generateRandomNumbers(20000);
        long startTime = System.currentTimeMillis();

        HazelcastInstance hazelcastInstance = Hazelcast.newHazelcastInstance();
        Map<String, Integer> map = hazelcastInstance.getMap("random_numbers");

        for (int i = 0; i < randomNumbers.size(); i++) {
            map.put("number_" + i, randomNumbers.get(i));
        }

        long endTime = System.currentTimeMillis();
        System.out.println("Time taken to store numbers: " + (endTime - startTime) + " ms");

        // Step 2: Retrieve the numbers randomly from HazelCast
        startTime = System.currentTimeMillis();

        List<Integer> retrievedNumbers = new ArrayList<>();
        for (int i = 0; i < randomNumbers.size(); i++) {
            int randomNumberIndex = new Random().nextInt(randomNumbers.size());
            int retrievedNumber = map.get("number_" + randomNumberIndex);
            retrievedNumbers.add(retrievedNumber);
        }

        endTime = System.currentTimeMillis();
        System.out.println("Time taken to retrieve numbers: " + (endTime - startTime) + " ms");

        // Optional: Print the retrieved numbers
        System.out.println("Retrieved numbers:");
        for (int number : retrievedNumbers) {
            System.out.println(number);
        }

        // Shutdown the HazelCast instance when done
        hazelcastInstance.shutdown();

        }
    private static List<Integer> generateRandomNumbers(int count) {
        List<Integer> numbers = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < count; i++) {
            numbers.add(random.nextInt(1000)); // Generate random numbers between 0 and 999
        }
        return numbers;
    }
    }

