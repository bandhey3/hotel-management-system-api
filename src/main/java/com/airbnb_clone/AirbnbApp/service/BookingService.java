package com.airbnb_clone.AirbnbApp.service;

import com.airbnb_clone.AirbnbApp.dto.BookingDto;
import com.airbnb_clone.AirbnbApp.dto.BookingRequest;
import com.airbnb_clone.AirbnbApp.dto.GuestDto;
import com.airbnb_clone.AirbnbApp.dto.HotelReportDto;
import com.stripe.model.Event;

import java.nio.file.AccessDeniedException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface BookingService {
    BookingDto initialiseBooking(BookingRequest bookingRequest);

    BookingDto addGuests(Long bookingId, List<GuestDto> guestDtoList);

    String initiatePayment(Long bookingId);

    void capturePayment(Event event);

    void cancelBooking(Long bookingId);

    String getBookingStatus(Long bookingId);

    List<BookingDto> getAllBookingsByHotelId(Long hotelId);

    HotelReportDto getHotelReport(Long hotelId, LocalDate startDate, LocalDate endDate);

    List<BookingDto> getMyBookings();
}
