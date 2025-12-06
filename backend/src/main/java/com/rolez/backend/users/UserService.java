package com.rolez.backend.users;

import com.rolez.backend.exception.DuplicateResourceException;
import com.rolez.backend.exception.ResourceNotFoundException;
import com.rolez.backend.s3.S3Buckets;
import com.rolez.backend.s3.S3Service;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
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
        checkIfUserExistsOrThrow(userId);
    }

    public void uploadUserImage(Integer userId, MultipartFile file) {
    }

    public byte[] getUserProfileImage(Integer userId) {
        return null;
    }
}
