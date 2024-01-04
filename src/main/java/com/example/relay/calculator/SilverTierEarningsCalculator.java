package com.example.relay.calculator;

import com.example.relay.model.Activity;
import com.example.relay.model.EarningsStatement;
import com.example.relay.model.LineItem;
import com.example.relay.util.DateTimeExtractor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
@Component
public class SilverTierEarningsCalculator implements EarningsCalculator{
    Logger LOGGER = Logger.getLogger(SilverTierEarningsCalculator.class.getName());
    final static String CARD_TIER = "silver_tier";
    final static double MINIMUM_EARNING = 13.5;
    final static Map<String,Double> lineItemsRates =
            Map.of("Per successful attempt",0.65,
                    "Per unsuccessful attempt" , 0.0,
                    "Loyalty bonus (attempts)" , 19.00);

    //Create a map to store the name and number of occurrence for each Line Item
    Map<String,Integer> lineItemsMap = new HashMap<>();
    @Override
    public String getName() {
        return CARD_TIER;
    }

    @Override
    public EarningsStatement calculateEarnings(List<Activity> activities) {

        //Isolate attemptDateTimes and create a new list, calculate total hours_worked
        List<String> attemptDateTimes = activities.stream()
                .map(Activity::getAttemptDateTime)
                .toList();
        double hours_worked = getHoursWorked(attemptDateTimes);
        double lineItemsSubtotal =0;

        //First instantiate the lineItems with all available lineItems (number of occurrence = 0)
        for(String lineItemName: lineItemsRates.keySet()){
            lineItemsMap.put(lineItemName,0);
        }

        //Populate the maps with the actual values
        populateMaps(activities);
        //Calculate total pay
        lineItemsSubtotal = getLineItemsSubtotal();
        //Add and bonus (if eligible)
        double finalEarnings = getFinalEarnings(lineItemsSubtotal);

        //Create and store all LineItems
        List<LineItem> lineItemsList = new ArrayList<>();
        for(Map.Entry<String,Integer> entry: lineItemsMap.entrySet()){
            String lineItemName = entry.getKey();
            Integer lineItemOccurrence = entry.getValue();;
            double totalEarning = lineItemOccurrence * lineItemsRates.get(lineItemName);
            LineItem lineItem = LineItem.builder()
                    .name(lineItemName)
                    .quantity(lineItemOccurrence)
                    .rate(lineItemsRates.get(lineItemName))
                    .total(totalEarning)
                    .build();

            lineItemsList.add(lineItem);
        }

        return EarningsStatement.builder()
                .lineItems(lineItemsList)
                .lineItemsSubtotal(lineItemsSubtotal)
                .hoursWorked(hours_worked)
                .minimumEarnings(MINIMUM_EARNING)
                .finalEarnings(finalEarnings)
                .build();
    }

    private double getLineItemsSubtotal() {
        return lineItemsMap. get("Per successful attempt") * lineItemsRates.get("Per successful attempt")
                + lineItemsMap. get("Per unsuccessful attempt") * lineItemsRates.get("Per unsuccessful attempt");
    }

    //Adds tier bonuses to eligible couriers
    private double getFinalEarnings (double subtotal){
        //Completed at least 150 successful attempts
        if(lineItemsMap.get("Per successful attempt")>= 150){
            subtotal += lineItemsRates.get("Loyalty bonus (attempts)");
            lineItemsMap.put("Loyalty bonus (attempts)",1);
        }
        return Math.max(subtotal, MINIMUM_EARNING);
    }
    private double getHoursWorked(List<String> attemptDateTimeList) {
        //Convert provided time entries from string to LocalDate type
        List<LocalDateTime> dateTimeList = DateTimeExtractor.getDateTimeList(attemptDateTimeList);

        //assuming all entries are in the same week
        //If we need to check that we can add a method here

        return DateTimeExtractor.calculateTotalHoursWorked(dateTimeList);
    }
    private void populateMaps(List<Activity> activities){
        for(Activity activity: activities){
            if(activity.isSuccess()){
                // Get the current count from the map, defaulting to 0 if not present
                int currentCount = lineItemsMap.getOrDefault("Per successful attempt", 0);
                // Update the count in the map
                lineItemsMap.put("Per successful attempt", currentCount + 1);
            }
            else{
                int currentCount = lineItemsMap.getOrDefault("Per unsuccessful attempt", 0);
                lineItemsMap.put("Per unsuccessful attempt", currentCount + 1);
            }

        }
    }
}
