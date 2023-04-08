package com.diploma.project.controller.diploma;

import com.diploma.project.model.oauth.Role;
import com.diploma.project.model.oauth.dto.UserDto;
import com.diploma.project.repository.oauth.RoleRepository;
import com.diploma.project.service.impl.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/users")
@Slf4j
//@PreAuthorize("hasRole('MODERATOR')")
public class UsersController {

    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private RoleRepository roleRepository;

    @GetMapping("/{id}")
    public UserDto get(@PathVariable("id") Long id) {
        return userService.getById(id);
    }


    @GetMapping("/all-users")
    @PreAuthorize("hasRole('MODERATOR')")
    public ResponseEntity<List<UserDto>> findAllUsers() {
        return ResponseEntity.ok().body(userService.getAllUsers());
    }

    @GetMapping("/not-enabled")
    @PreAuthorize("hasRole('MODERATOR')")
    public ResponseEntity<List<UserDto>> findUsersFromRegistrationRequest() {
        return ResponseEntity.ok().body(userService.getAllNotEnabledUsers());
    }

//    @PutMapping("/activate-user")
//    @PreAuthorize("hasRole('MODERATOR')")
//    public ResponseEntity<Boolean> activateUser(@RequestParam("userId") Long userId,
//                                                @AuthenticationPrincipal JwtUser currentUser,
//                                                HttpServletRequest request) {
//
//        return ResponseEntity.ok().body(userService.activateUser(userId));
//    }

    @PutMapping("/decline-user")
    @PreAuthorize("hasRole('MODERATOR')")
    public ResponseEntity<Boolean> declineUser(@RequestParam("userId") Long userId) {
        return ResponseEntity.ok().body(userService.declineUser(userId));
    }

    @PutMapping("/disable-user")
    @PreAuthorize("hasRole('MODERATOR')")
    public ResponseEntity<Boolean> disableUser(@RequestParam("userId") Long userId) {
        return ResponseEntity.ok().body(userService.disableUser(userId));
    }

    @PutMapping("/change-password/{userId}")
    public ResponseEntity<String> changePassword(@PathVariable("userId") Long userId,
                                                 @RequestParam("oldPassword") String oldPassword,
                                                 @RequestParam("newPassword") String newPassword) {
        return ResponseEntity.ok().body(userService.changePassword(oldPassword, newPassword, userId));
    }


    @GetMapping("/roles")
    public ResponseEntity<List<Role>> getRoles() {
        return ResponseEntity.ok().body(roleRepository.findAll());
    }


    @PutMapping("/change-role/{userId}")
    public ResponseEntity<UserDto> changeRole(@PathVariable("userId") Long userId,
                                              @RequestParam("newRole") Long newRole) {
        log.info("REST request to change role for USER WITH ID : {}", userId);
        return ResponseEntity.ok().body(userService.changeRole(newRole, userId));
    }

    @PutMapping("/change-email/{userId}")
    public ResponseEntity<UserDto> changeEmail(@PathVariable("userId") Long userId,
                                               @RequestParam("email") String email) {
        return ResponseEntity.ok().body(userService.changeEmail(email, userId));
    }


    @PutMapping("/change-user-info")
    public ResponseEntity<?> changeUserInfo( @RequestBody UserDto userDto){
        if(userDto.getId()!=null){
            return ResponseEntity.ok().body(userService.changeInfo(userDto));
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Не удалось изменить пользователя");
    }


}
