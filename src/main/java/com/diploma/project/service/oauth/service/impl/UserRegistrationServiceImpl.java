package com.diploma.project.service.oauth.service.impl;
import com.diploma.project.exception.util.ThrowExceptionUtil;
import com.diploma.project.model.homePage.ClinicList;
import com.diploma.project.model.homePage.DoctorList;
import com.diploma.project.model.oauth.EUserStatus;
import com.diploma.project.model.oauth.Role;
import com.diploma.project.model.oauth.User;
import com.diploma.project.model.oauth.dto.UserDto;
import com.diploma.project.repository.homePage.ClinicListRepository;
import com.diploma.project.repository.homePage.DoctorListRepository;
import com.diploma.project.repository.oauth.RoleRepository;
import com.diploma.project.repository.oauth.UserRepository;
import com.diploma.project.service.oauth.service.UserRegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.io.IOException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserRegistrationServiceImpl implements UserRegistrationService {

    private final PasswordEncoder passwordEncoder;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private DoctorListRepository doctorListRepository;
    @Autowired
    private ClinicListRepository clinicListRepository;

    @Override
    @Transactional
    public Boolean registerUser(UserDto userDto) throws IOException {
        User user = new User(userDto.getLastName(), userDto.getFirstName(), userDto.getPatronymic(),
                userDto.getEmail(), userDto.getPhoneNumber(), passwordEncoder.encode(userDto.getPassword()));

        user.setStatus(EUserStatus.ACTIVE);
        if(userDto.getRole()!=null){
            user.setRole(roleRepository.findById(userDto.getRole())
                    .orElseThrow(ThrowExceptionUtil.throwCustomExceptionByCodeNF012(userDto.getRole().toString(), Role.class)));
        }
        final User savedUser = userRepository.save(user);

        if(userDto.getRole()!=null && userDto.getRole()==1){
            DoctorList doctorList = new DoctorList();
            doctorList.setUser(savedUser);
            if(userDto.getClinicId()!=null){
                Optional<ClinicList> clinicListOptional = clinicListRepository.findById(userDto.getClinicId());
                if(clinicListOptional.isPresent()){
                    doctorList.setClinicList(clinicListOptional.get());
                }
            }
            doctorListRepository.save(doctorList);
        }

        return savedUser.getId() != null;
    }
}
