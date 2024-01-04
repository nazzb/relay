package com.example.relay.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class LineItem {
    private String name;
    private int quantity;
    private double rate;
    private double total;
}
