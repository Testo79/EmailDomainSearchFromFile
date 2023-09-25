import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class EmailDomainSearchFromFile {

    public static void main(String[] args) {
        String inputFilePath = "emails.txt"; // Replace with the path to your input text file
        String targetDomain = "gmail.com";
        String outputFilePath = "results.txt"; // Replace with the desired output file path

        // Number of threads to use
        int numThreads = 3;

        ExecutorService executorService = Executors.newFixedThreadPool(numThreads);

        List<Future<String>> futures = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                final String email = line;
                Future<String> future = executorService.submit(() -> {
                    // Check if the email contains the target domain
                    return email;
                });
                futures.add(future);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Wait for all threads to finish and collect the results
        List<String> results = new ArrayList<>();
        for (Future<String> future : futures) {
            try {
                results.add(future.get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }

        // Shutdown the executor service
        executorService.shutdown();

        // Write results to the output file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath))) {
            for (String result : results) {
                writer.write(result);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
