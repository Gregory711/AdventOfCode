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
        int testsPassed = 0;

        for (InputStream testFile : testFiles) {

            ArrayList<String> inputData = getInputData(testFile);
            String answer = inputData.get(0);
            inputData.remove(0);

            Problem problem = new Problem(day, inputData, answer);
            Map<String, String> testResult = problem.getResults();
            if (testResult.get("result").equals("PASS")) {
                testsPassed++;
            }
            
            testResults.add(testResult);
        }

        report.put("tests", testResults);
        report.put("tests passed", testsPassed + "/" + testFiles.size());

        ClassPathResource resource = new ClassPathResource(day + "input.txt");
        InputStream inputFile;
        try {
            inputFile = resource.getInputStream();
            ArrayList<String> inputData = getInputData(inputFile);
            Problem problem = new Problem(day, inputData, null);
            Map<String, String> inputResult = problem.getResults();
            report.put("input", inputResult);
        } catch (IOException e) {
            report.put("error", "No input file found");
        }

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
