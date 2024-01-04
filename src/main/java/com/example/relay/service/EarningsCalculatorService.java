package com.example.relay.service;

import com.example.relay.model.Activity;
import com.example.relay.calculator.EarningsCalculator;
import com.example.relay.model.EarningsStatement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
@Service
public class EarningsCalculatorService {
    private final Map<String, EarningsCalculator> calculatorMap;

    @Autowired
    public EarningsCalculatorService(List<EarningsCalculator> calculators) {
        // Initialize the calculatorMap with available implementations
        this.calculatorMap = calculators.stream()
                .collect(Collectors.toMap(EarningsCalculator::getName, Function.identity()));
    }

    public EarningsStatement calculateEarnings(String rateCardId, List<Activity> activityList) {
        // Retrieve the appropriate calculator based on rateCardId
        EarningsCalculator earningsCalculator = calculatorMap.get(rateCardId);

        if (earningsCalculator == null) {
            throw new IllegalArgumentException("Invalid rate card ID: " + rateCardId);
        }

        // Call the selected calculator to calculate earnings
        return earningsCalculator.calculateEarnings(activityList);
    }
}
