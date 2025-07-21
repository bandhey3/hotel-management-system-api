package com.airbnb_clone.AirbnbApp.service;

import com.airbnb_clone.AirbnbApp.dto.HotelDto;
import com.airbnb_clone.AirbnbApp.dto.HotelInfoDto;
import com.airbnb_clone.AirbnbApp.dto.RoomDto;
import com.airbnb_clone.AirbnbApp.entity.Hotel;
import com.airbnb_clone.AirbnbApp.entity.Room;
import com.airbnb_clone.AirbnbApp.entity.User;
import com.airbnb_clone.AirbnbApp.exception.ResourceNotFoundException;
import com.airbnb_clone.AirbnbApp.exception.UnAuthorisedException;
import com.airbnb_clone.AirbnbApp.repository.HotelRepository;
import com.airbnb_clone.AirbnbApp.repository.RoomRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.airbnb_clone.AirbnbApp.util.AppUtils.getCurrentUser;

@Service
@RequiredArgsConstructor
@Slf4j
public class HotelServiceSet implements HotelService {

    private final HotelRepository hotelRepository;
    private final ModelMapper modelMapper;
    private final InventoryService inventoryService;
    private final RoomRepository roomRepository;

    @Override
    public HotelDto createNewHotel(HotelDto hotelDto) {
        log.info("Creating a new hotel with name: {}", hotelDto.getName());
        Hotel hotel = modelMapper.map(hotelDto, Hotel.class);
        hotel.setActive(false);

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        hotel.setOwner(user);
        hotel = hotelRepository.save(hotel);
        log.info("Created a new hotel with Id: {}", hotelDto.getId());
        return modelMapper.map(hotel, HotelDto.class);
    }


    @Override
    public List<HotelDto> getAllHotel() {
        User user = getCurrentUser();
        log.info("Getting all hotels for the admin user with ID: {}",user.getId());
        List<Hotel> hotels = hotelRepository.findByOwner(user);

        return hotels
                .stream()
                .map((element) -> modelMapper.map(element, HotelDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public HotelDto getHotelById(Long id) {
        log.info("getting the hotel with Id: {}", id);
        Hotel hotel = hotelRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel not found with ID: " + id));

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!user.equals(hotel.getOwner())){
            throw new UnAuthorisedException("This user doesn't own this hotel with id: "+id);
        }

        return modelMapper.map(hotel, HotelDto.class);
    }

    @Override
    public HotelDto updateHotelById(Long id, HotelDto hotelDto) {
        log.info("updating the hotel with Id: {}", id);
        Hotel hotel = hotelRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Hotel not found with ID: " + id));

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!user.equals(hotel.getOwner())){
            throw new UnAuthorisedException("This user doesn't own this hotel with id: "+id);
        }

        modelMapper.map(hotelDto, hotel);
        hotel.setId(id);
        hotel = hotelRepository.save(hotel);
        return modelMapper.map(hotel, HotelDto.class);
    }

    @Override
    @Transactional
    public void deleteHotelById(Long id) {
        Hotel hotel = hotelRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel not found with ID: " + id));

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!user.equals(hotel.getOwner())){
            throw new UnAuthorisedException("This user doesn't own this hotel with id: "+id);
        }


        for (Room room: hotel.getRoom()){
            inventoryService.deleteAllInventories(room);
            roomRepository.deleteById(room.getId());
        }
        hotelRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void activateHotel(Long id) {
        log.info("activating the hotel with Id: {}", id);
        Hotel hotel = hotelRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Hotel not found with ID: " + id));

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!user.equals(hotel.getOwner())){
            throw new UnAuthorisedException("This user doesn't own this hotel with id: "+id);
        }


        hotel.setActive(true);

        // assuming only do it once
        for (Room room: hotel.getRoom()){
            inventoryService.initializeRoomForAYear(room);
        }
    }

    @Override
    public HotelInfoDto getHotelInfoById(Long hotelId) {
        Hotel hotel = hotelRepository.findById(hotelId)
                .orElseThrow(()-> new ResourceNotFoundException("Hotel not found with ID: "+hotelId));

        List<RoomDto> rooms = hotel.getRoom()
                .stream()
                .map((element) -> modelMapper.map(element, RoomDto.class))
                .toList();


        return new HotelInfoDto(modelMapper.map(hotel, HotelDto.class),rooms);
    }


}
