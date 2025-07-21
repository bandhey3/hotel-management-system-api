package com.airbnb_clone.AirbnbApp.strategy;

import com.airbnb_clone.AirbnbApp.entity.Inventory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;


public class BasePriceStrategy implements PricingStrategy{


    @Override
    public BigDecimal calculatePrice(Inventory inventory) {
        return inventory.getRoom().getBasePrice();
    }
}
