package com.airbnb_clone.AirbnbApp.dto;

import com.airbnb_clone.AirbnbApp.entity.Hotel;
import com.airbnb_clone.AirbnbApp.entity.Room;
import com.airbnb_clone.AirbnbApp.entity.User;
import com.airbnb_clone.AirbnbApp.entity.enums.BookingStatus;
import lombok.Data;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;



@Data
public class BookingDto {

    private Long id;
    private Integer roomsCount;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private BookingStatus bookingStatus;
    private Set<GuestDto> guests;
    private BigDecimal amount;

}
