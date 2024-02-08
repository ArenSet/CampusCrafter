package campusCrafter.repository;

import campusCrafter.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    User findByEmail(String email);

    Boolean existsByEmail(String email);

    User findById(int id);


    @Transactional
    @Modifying
    @Query(nativeQuery = true,value = "update `user` set `name` = ?1, `password` = ?2, `profile_picture` = ?3, `bio` = ?4 where id = ?5")
    void updateUser(Optional<String> name, Optional<String> password, Optional<String> profilePicture, Optional<String> bio, int id);
}
