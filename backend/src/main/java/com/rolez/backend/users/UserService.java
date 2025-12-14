package com.rolez.backend.users;

import ch.qos.logback.core.util.StringUtil;
import com.rolez.backend.exception.DuplicateResourceException;
import com.rolez.backend.exception.ResourceNotFoundException;
import com.rolez.backend.s3.S3Buckets;
import com.rolez.backend.s3.S3Service;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.utils.StringUtils;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserDao userDao;
    private final UserDTOMapper userDTOMapper;
    private final PasswordEncoder passwordEncoder;
    private final S3Buckets s3Buckets;
    private final S3Service s3Service;

    public UserService(@Qualifier("jdbc") UserDao userDao,
                       UserDTOMapper userDTOMapper,
                       PasswordEncoder passwordEncoder,
                       S3Buckets s3Buckets,
                       S3Service s3Service) {
        this.userDao = userDao;
        this.userDTOMapper = userDTOMapper;
        this.passwordEncoder = passwordEncoder;
        this.s3Buckets = s3Buckets;
        this.s3Service = s3Service;
    }


    public List<UserDTO> getAllUsers() {
        return userDao.selectAll()
                .stream()
                .map(userDTOMapper)
                .collect(Collectors.toList());
    }

    public UserDTO getUser(Integer userId) {

        return userDao.selectUserById(userId)
                .map(userDTOMapper)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "customer with id [%s] not found".formatted(userId)
                ));
    }

    public void addUser(UserRegistrationRequest request) {

        String email = request.email();
        if(userDao.existsUserByEmail(email)) {
            throw new DuplicateResourceException("User with email already exists.");
        }

        User user = new User(
                request.username(),
                request.name(),
                request.email(),
                passwordEncoder.encode(request.password())
        );
        userDao.insertUser(user);
    }

    public void deleteUserById(Integer userId) {
        checkIfUserExistsOrThrow(userId);
        userDao.deleteUserById(userId);
    }

    private void checkIfUserExistsOrThrow(Integer userId) {
        if(!userDao.existsUserById(userId)) {
            throw new ResourceNotFoundException("user with id [%s] not found".formatted(userId));
        }
    }

    public void updateUser(Integer userId, UserRegistrationRequest request) {

        User user = userDao.selectUserById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("customer with id [%s] not found".formatted(userId)));

        boolean changes = false;

        if(request.name() != null && !request.name().equals(user.getName())) {
            user.setName(request.name());
            changes = true;
        }

        if(request.email() != null && !request.email().equals(user.getEmail())) {
            if(userDao.existsUserByEmail(request.email())) {
                throw new DuplicateResourceException("User with email already exists.");
            }
            user.setEmail(request.email());
            changes = true;
        }

        userDao.updateUser(user);

    }

    public void uploadUserImage(Integer userId, MultipartFile file) {

        checkIfUserExistsOrThrow(userId);
        String profileImageId = UUID.randomUUID().toString();
        try{
            s3Service.putObject(
                    s3Buckets.getUser(),
                    "profile-images/%s/%s".formatted(userId, profileImageId),
                    file.getBytes()
            );
        }catch (IOException e ){
            throw new RuntimeException("failed to upload profile image", e);
        }

        userDao.updateUserProfileImageId(profileImageId, userId);
    }

    public byte[] getUserProfileImage(Integer userId) {
        var user = userDao.selectUserById(userId)
                .map(userDTOMapper)
                .orElseThrow(() -> new ResourceNotFoundException("user with id [%s] not found".formatted(userId)));

        if(StringUtils.isBlank(user.profilePictureUrl())){
            throw new  ResourceNotFoundException("user profile image not found");
        }

        byte[] profileImage = s3Service.getObject(
                s3Buckets.getUser(),
                "profile-images/".formatted(userId,  user.profilePictureUrl())
        );
        return profileImage;
    }
}
