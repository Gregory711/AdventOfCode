package com.advent.app;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DayController {
    
    @GetMapping("/day")
    public ResponseEntity<Map<String, Object>> day(@RequestParam int day) {
        Map<String, Object> data = new HashMap<>();
        data.put("answer", generateReport(day));
        return ResponseEntity.status(HttpStatus.OK).body(data);
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
    private String generateReport(int day) {
        return "ToDo: Implement me!";
    }
}