package com.rolez.backend.users;

import com.rolez.backend.jwt.JWTService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("api/v1/users")
public class UserController {

    private UserService userService;
    private final JWTService jwtService;

    public UserController(UserService userService, JWTService jwtService) {
        this.userService = userService;
        this.jwtService = jwtService;
    }

    @GetMapping
    public List<UserDTO> getUsers() {return userService.getAllUsers();}

    @GetMapping("{userId}")
    public UserDTO getUser(@PathVariable("userId") Integer userId) {
        return userService.getUser(userId);
    }

    @PostMapping
    public ResponseEntity<?> registerUser(@RequestBody UserRegistrationRequest request) {
        userService.addUser(request);
        String jwtToken = jwtService.issueToken(request.email(), "ROLE_USER");
        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION,  jwtToken)
                .build();
    }

    @DeleteMapping("{userId}")
    public void deleteUser(@PathVariable("userId") Integer userId) {
        userService.deleteUserById(userId);
    }

    @PutMapping("{userId}")
    public void updateUser(
            @PathVariable("userId") Integer userId,
            @RequestBody UserRegistrationRequest request){
        userService.updateUser(userId, request);
    }

    @PostMapping(
            value = "{userId}/profile-image",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public void uploadUserProfileImage(
            @PathVariable("userId") Integer userId,
            @RequestParam("file") MultipartFile file
    ){
        userService.uploadUserImage(userId, file);
    }

    @GetMapping(
            value = "{customerId}/profile-image",
            produces = MediaType.IMAGE_JPEG_VALUE
    )

    public byte[] getUserProfileImage(@PathVariable("userId") Integer userId) {
        return userService.getUserProfileImage(userId);
    }

}
