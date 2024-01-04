package com.example.relay.calculator;

import com.example.relay.model.Activity;
import com.example.relay.model.EarningsStatement;

import java.util.List;

public interface EarningsCalculator {
    String getName();
    EarningsStatement calculateEarnings(List<Activity> activityList);
}
