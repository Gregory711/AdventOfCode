package com.advent.app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DayController {
    
    @GetMapping("/day")
    public ResponseEntity<Map<String, Object>> day(@RequestParam int day) {
        return ResponseEntity.status(HttpStatus.OK).body(generateReport(day));
    }

    /*
     * Generates a report for the given day of the month containing:
     * For each ${day}test${numb}.txt file in the resources folder:
     *   - The calculated answer and if it matches the expected answer
     *   - The time taken to calculate the answer
     * For the ${day}input.txt file in the resources folder:
     *   - The calculated answer
     *   - The time taken to calculate the answer
     * @param day The day of the month to generate a report for
     * @return The report for the given day
     */
    private Map<String, Object> generateReport(final int day) {

        Map<String, Object> report = new HashMap<>();

        ArrayList<InputStream> testFiles = getTestFiles(day);
        ArrayList<Map<String, String>> testResults = new ArrayList<>();

        for (InputStream testFile : testFiles) {

            ArrayList<String> inputData = getInputData(testFile);
            String answer = inputData.get(0);
            inputData.remove(0);

            long startTime = System.nanoTime();
            String output;
            switch(day) {
                case 1:
                    output = Integer.toString(new CalorieCounter(inputData).getMostCalories());
                    break;
                default:
                    output = "ToDo: Implement me!";
            }
            long endTime = System.nanoTime();
            long timeTaken = endTime - startTime;
            double timeTakenSeconds = (double) timeTaken / 1_000_000_000;
            
            Map<String, String> testResult = new HashMap<>();
            testResult.put("expected", answer);
            testResult.put("actual", output);
            if (answer.equals(output)) {
                testResult.put("result", "PASS");
            } else {
                testResult.put("result", "FAIL");
            }
            testResult.put("time (seconds)", Double.toString(timeTakenSeconds));
            testResults.add(testResult);
        }

        report.put("tests", testResults);

        return report;
    }

    /*
     * Returns ordered ArrayList of all test files for the given day
     * @param day The day of the month to get test files for
     * @return The ordered ArrayList of all test files for the given day
     */
    private ArrayList<InputStream> getTestFiles(final int day) {
        ArrayList<InputStream> testFiles = new ArrayList<>();
        int i = 1;
        while (true) {
            ClassPathResource resource = new ClassPathResource(day + "test" + i + ".txt");
            InputStream testFile;
            try {
                testFile = resource.getInputStream();
            } catch (IOException e) {
                return testFiles;
            }
            testFiles.add(testFile);
            i++;
        }
    }

    /*
     * Returns ordered ArrayList of all input data line by line
     * @param file The file to get input data from
     * @return The ordered ArrayList of all input data line by line
     */
    private ArrayList<String> getInputData(final InputStream file) {
        ArrayList<String> inputData = new ArrayList<>();
        try (InputStreamReader isr = new InputStreamReader(file);
                BufferedReader br = new BufferedReader(isr);) {
            String line;
            while ((line = br.readLine()) != null) {
                inputData.add(line);
            }
        } catch (IOException e) {}
        return inputData;
    }
}
