package com.airbnb_clone.AirbnbApp.dto;

import com.airbnb_clone.AirbnbApp.entity.HotelContactInfo;
import lombok.Data;



@Data
public class HotelDto {

    private Long id;
    private String name;
    private String city;
    private String[] photos;
    private String[]  amenities;
    private HotelContactInfo contactInfo;
    private Boolean active;

}
