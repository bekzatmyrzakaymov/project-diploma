package com.diploma.project.service.oauth.service;

import com.diploma.project.model.oauth.dto.UserDto;
import java.io.IOException;

public interface UserRegistrationService {

    Boolean registerUser(UserDto userDto) throws IOException;

}
