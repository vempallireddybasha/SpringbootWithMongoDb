package com.mongodb.collections;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Address {
    private String address1;
    private int pincode;
    private String city;
    private String state;
}