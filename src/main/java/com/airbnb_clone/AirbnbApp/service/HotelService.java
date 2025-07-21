package com.airbnb_clone.AirbnbApp.service;


import com.airbnb_clone.AirbnbApp.dto.HotelDto;
import com.airbnb_clone.AirbnbApp.dto.HotelInfoDto;

import java.util.List;


public interface HotelService {

    HotelDto createNewHotel(HotelDto hotelDto);

    List<HotelDto> getAllHotel();

    HotelDto getHotelById(Long id);

    HotelDto updateHotelById(Long id, HotelDto hotelDto);

    void deleteHotelById(Long id);

    void activateHotel(Long id);

    HotelInfoDto getHotelInfoById(Long hotelId);
}
