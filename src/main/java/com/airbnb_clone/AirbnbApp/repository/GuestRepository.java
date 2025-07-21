package com.airbnb_clone.AirbnbApp.repository;

import com.airbnb_clone.AirbnbApp.entity.Guest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GuestRepository extends JpaRepository<Guest, Long> {
}