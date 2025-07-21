package com.airbnb_clone.AirbnbApp.controller;


import com.airbnb_clone.AirbnbApp.dto.HotelDto;
import com.airbnb_clone.AirbnbApp.dto.HotelInfoDto;
import com.airbnb_clone.AirbnbApp.dto.HotelPriceDto;
import com.airbnb_clone.AirbnbApp.dto.HotelSearchRequest;
import com.airbnb_clone.AirbnbApp.service.HotelService;
import com.airbnb_clone.AirbnbApp.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/hotels")
@RequiredArgsConstructor
public class HotelBrowseController {

    private final InventoryService inventoryService;
    private final HotelService hotelService;

    @GetMapping("/search")
    public ResponseEntity<Page<HotelPriceDto>> searchHotels (@RequestBody HotelSearchRequest hotelSearchRequest){
        var page = inventoryService.searchHotels(hotelSearchRequest);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{hotelId}/info")
    public ResponseEntity<HotelInfoDto> getHotelInfo(@PathVariable Long hotelId){
        return ResponseEntity.ok(hotelService.getHotelInfoById(hotelId));
    }


}
