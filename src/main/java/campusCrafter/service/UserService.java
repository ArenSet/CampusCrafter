package campusCrafter.service;

import campusCrafter.model.User;
import campusCrafter.model.dto.UserRequestDto;
import campusCrafter.util.exceptions.DuplicateException;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    User getByUsername(String email);

    void create(UserRequestDto requestDto) throws DuplicateException;

    User getUserProfile(int id);

    void updateUser(User user, String name, String password, String profilePicture, String bio, int id);

    void deleteUser(User user);
}
