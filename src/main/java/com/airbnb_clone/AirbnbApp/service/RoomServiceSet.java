package com.airbnb_clone.AirbnbApp.service;


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
public class RoomServiceSet implements RoomService{

    private final RoomRepository roomRepository;
    private final HotelRepository hotelRepository;
    private final InventoryService inventoryService;
    private final ModelMapper modelMapper;

    @Override
    public RoomDto createNewRoom(Long hotelId,RoomDto roomDto) {
        log.info("Creating a new room in hotel with id: {}", hotelId);
        Hotel hotel = hotelRepository.findById(hotelId)
                .orElseThrow(()-> new ResourceNotFoundException("Hotel not found with id: "+hotelId));

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!user.equals(hotel.getOwner())){
            throw new UnAuthorisedException("This user doesn't own this hotel with id: "+hotelId);
        }

        Room room = modelMapper.map(roomDto,Room.class);
        room.setHotel(hotel);
        room = roomRepository.save(room);

        if (hotel.getActive()){
            inventoryService.initializeRoomForAYear(room);
        }

        return modelMapper.map(room,RoomDto.class);

    }

    @Override
    public List<RoomDto> getAllRoomInHotel(Long hotelId) {
        log.info("Getting all rooms in hotel with id: {}", hotelId);
        Hotel hotel = hotelRepository.findById(hotelId)
                .orElseThrow(()-> new ResourceNotFoundException("Hotel not found with id: "+hotelId));

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!user.equals(hotel.getOwner())){
            throw new UnAuthorisedException("This user doesn't own this hotel with id: "+hotelId);
        }

        return hotel.getRoom().stream().map((element) -> modelMapper.map(element, RoomDto.class)).collect(Collectors.toList());

    }

    @Override
    public RoomDto getRoomById(Long roomId) {
        log.info("Getting the rooms with id: {}", roomId);
        Room room = roomRepository.findById(roomId)
                .orElseThrow(()-> new ResourceNotFoundException("Room not found with id: "+roomId));

        return modelMapper.map(room,RoomDto.class);

    }

    @Override
    @Transactional
    public void deleteRoomById(Long roomId) {
        log.info("Deleting the rooms with id: {}", roomId);
        Room room = roomRepository.findById(roomId)
                .orElseThrow(()-> new ResourceNotFoundException("Room not found with id: "+roomId));

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!user.equals(room.getHotel().getOwner())){
            throw new UnAuthorisedException("This user doesn't own this hotel with id: "+roomId);
        }

        inventoryService.deleteAllInventories(room);
        roomRepository.deleteById(roomId);


    }

    @Override
    public RoomDto updateRoomById(Long hotelId, Long roomId, RoomDto roomDto) {
        log.info("Updating the room with id: {}", roomId);
        Hotel hotel = hotelRepository.findById(hotelId)
                .orElseThrow(()-> new ResourceNotFoundException("Hotel not found with id: "+hotelId));

        User user = getCurrentUser();
        if (!user.equals(hotel.getOwner())){
            throw new UnAuthorisedException("This user doesn't own this hotel with id: "+hotelId);
        }

        Room room = roomRepository.findById(roomId)
                .orElseThrow(()-> new ResourceNotFoundException("Room not found with Id: "+roomId));

        modelMapper.map(roomDto, room);
        room.setId(roomId);

        // TODO: if price or inventory is updated, then update the inventory for this room

        room = roomRepository.save(room);

        return modelMapper.map(room, RoomDto.class);
    }
}
