package com.airbnb_clone.AirbnbApp.service;

import com.airbnb_clone.AirbnbApp.entity.Booking;

public interface CheckoutService {
    String getCheckoutSession(Booking booking, String successUrl, String failureUrl);
}
