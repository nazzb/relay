package com.example.relay.controller;

import com.example.relay.model.Activity;
import com.example.relay.model.EarningsStatement;
import com.example.relay.service.EarningsCalculatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/earnings")
public class EarningsController {

    private final EarningsCalculatorService earningsCalculatorService;
    @Autowired
    public EarningsController(EarningsCalculatorService earningsCalculatorService){
        this.earningsCalculatorService = earningsCalculatorService;
    }

    @PostMapping("/{rateCardId}")
    public ResponseEntity<EarningsStatement> calculateEarnings(
            @PathVariable String rateCardId,
            @RequestBody List<Activity> activityList) {

        // Call a service method to calculate earnings based on rateCardId and activityList
        EarningsStatement earningsStatement = earningsCalculatorService.calculateEarnings(rateCardId,activityList);

        return new ResponseEntity<>(earningsStatement, HttpStatus.OK);
    }
}

