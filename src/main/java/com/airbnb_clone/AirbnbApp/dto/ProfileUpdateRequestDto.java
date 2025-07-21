package com.airbnb_clone.AirbnbApp.dto;


import com.airbnb_clone.AirbnbApp.entity.enums.Gender;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ProfileUpdateRequestDto {

    private String name;
    private LocalDate dateOfBirth;
    private Gender gender;

}
