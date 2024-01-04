# Parcel Delivery Earning Calculator

## Problem Statement

- We need an endpoint that accepts a list of activities
- Each activity describes a successful/unsuccessful attempt to deliver a parcel.
- We want to calculate an earning statement for each list, considering different tier categories.

## Requirements
- Each tier category comes with different rates for successful/unsuccessful attempts and different types of bonuses that needs to be applied.
- Each category has a minimum earnings requirement.
- If a courier's final earning is less than the minimum earnings, the minimum earnings will be paid to them. (this is what I understood from the provided document - I would double-check this for a production endpoint)

## Design decisions walkthrough
- We have an endpoint under the controller package
- The endpoint passes the tier_category and a list of activities to a service
- The service calls an Earning interface which implements different logics for each tier
- Each implementation is fully backward compatible, this means changing/updating/deleting/adding a tier does not affect other tiers.
- There is a helper method under the util package to assist with DataTime conversion which is needed in all implementations, for the return statement.

## Data Structure
- All implementations take advantage of HashMap data structure 
- This is for maintainability and taking advantage of 0(1) time complexity while searching a key 
- How does it help with maintainability? For example, the "lineItemsRates" map keeps one source of truth the rates within each implementation
- Where does the 0(1) time complexity come in handy? Within the bronze_tier, we have "Loyalty bonus (routes)". This bonus requires a data structure which can look up unique routeIds -> we do this by search a map