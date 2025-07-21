package com.airbnb_clone.AirbnbApp.service;

import com.airbnb_clone.AirbnbApp.dto.ProfileUpdateRequestDto;
import com.airbnb_clone.AirbnbApp.dto.UserDto;
import com.airbnb_clone.AirbnbApp.entity.User;

public interface UserService {

    User getUserById(Long id);

    void updateProfile(ProfileUpdateRequestDto profileUpdateRequestDto);

    UserDto getMyProfile();
}
