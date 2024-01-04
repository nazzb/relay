package com.example.relay.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Activity {
    private String routeId;
    private String attemptDateTime;
    private boolean success;
}
