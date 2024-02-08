package campusCrafter.service.impl;

import campusCrafter.model.User;
import campusCrafter.model.dto.UserRequestDto;
import campusCrafter.repository.UserRepository;
import campusCrafter.service.UserService;
import campusCrafter.util.ErrorMessage;
import campusCrafter.util.exceptions.DuplicateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public User getByUsername(String email){
        return userRepository.findByEmail(email);
    }

    @Override
    public void create(UserRequestDto requestDto) throws DuplicateException {
        if (userRepository.existsByEmail(requestDto.getEmail())){
            throw new DuplicateException(ErrorMessage.DUPLICATE_EMAIL);
        }

        User user = convertToUser(requestDto, new User());
        userRepository.save(user);
    }

    @Override
    public User getUserProfile(int id){
        return userRepository.findById(id);
    }

    @Override
    public void updateUser(User user, String name, String password, String profilePicture, String bio, int id){
        Optional<String> optionalName = Optional.of(Optional.ofNullable(name).orElse(user.getName()));
        Optional<String> optionalPassword = Optional.of(Optional.ofNullable(password).orElse(user.getPassword()));
        Optional<String> optionalProfilePicture = Optional.of(Optional.ofNullable(profilePicture).orElse(user.getProfilePicture()));
        Optional<String> optionalBio = Optional.of(Optional.ofNullable(bio).orElse(user.getBio()));

        userRepository.updateUser(optionalName,optionalPassword,optionalProfilePicture,optionalBio, id);
    }

    @Override
    public void deleteUser(User user){
        userRepository.delete(user);
    }

    private User convertToUser(UserRequestDto userRequestDto, User user){

        user.setName(userRequestDto.getName());
        user.setEmail(userRequestDto.getEmail());
        user.setRole(userRequestDto.getRoles());
        user.setPassword(passwordEncoder.encode(userRequestDto.getPassword()));
        user.setDataJoined(currentDate());
        user.setLastLogin(currentDate());
        user.setProfilePicture(userRequestDto.getProfilePicture());
        user.setBio(userRequestDto.getBio());

        return user;
    }

    private String currentDate(){
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat form = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = form.format(date);

        return formattedDate;
    }
}
