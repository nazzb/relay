package com.example.relay.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class EarningsStatement {
    private List<LineItem> lineItems;
    private double lineItemsSubtotal;
    private double hoursWorked;
    private double minimumEarnings;
    private double finalEarnings;
}
