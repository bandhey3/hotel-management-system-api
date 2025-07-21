package com.airbnb_clone.AirbnbApp.strategy;

import com.airbnb_clone.AirbnbApp.entity.Inventory;


import java.math.BigDecimal;



public interface PricingStrategy {

    BigDecimal calculatePrice(Inventory inventory);

}


