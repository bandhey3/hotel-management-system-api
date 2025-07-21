package com.airbnb_clone.AirbnbApp.controller;

import com.airbnb_clone.AirbnbApp.dto.BookingDto;
import com.airbnb_clone.AirbnbApp.dto.ProfileUpdateRequestDto;
import com.airbnb_clone.AirbnbApp.dto.UserDto;
import com.airbnb_clone.AirbnbApp.entity.User;
import com.airbnb_clone.AirbnbApp.service.BookingService;
import com.airbnb_clone.AirbnbApp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final BookingService bookingService;


    @PatchMapping("/profile")
    public ResponseEntity<Void> updateProfile(@RequestBody ProfileUpdateRequestDto profileUpdateRequestDto){
        userService.updateProfile(profileUpdateRequestDto);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/myBookings")
    public ResponseEntity<List<BookingDto>> getMyBookings(){
        return ResponseEntity.ok(bookingService.getMyBookings());
    }

    @GetMapping("/profile")
    public ResponseEntity<UserDto> getMyProfile(){
        return ResponseEntity.ok(userService.getMyProfile());
    }

}
