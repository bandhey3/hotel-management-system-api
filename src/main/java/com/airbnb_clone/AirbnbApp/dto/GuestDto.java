package com.airbnb_clone.AirbnbApp.dto;

import com.airbnb_clone.AirbnbApp.entity.User;
import com.airbnb_clone.AirbnbApp.entity.enums.Gender;
import lombok.Data;

@Data
public class GuestDto {

    private Long id;
    private User users;
    private String name;
    private Gender gender;
    private Integer age;

}
