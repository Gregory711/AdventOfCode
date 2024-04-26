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
    public ResponseEntity<Map<String, Object>> day(@RequestParam String day, @RequestParam String file) {
        Map<String, Object> data = new HashMap<>();
        data.put("answer", calculateAnswer(day, file));
        return ResponseEntity.status(HttpStatus.OK).body(data);
    }

    private String calculateAnswer(String day, String file) {
        return "ToDo: Implement me!";
    }
}
