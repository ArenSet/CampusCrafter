package campusCrafter.repository;

import campusCrafter.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Course, Integer> {

    List<Course> findAll();

    Course findById(int id);

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "update `course` set `title` = ?1, `description` = ?2, `credits` = ?3, `enrollment_limit` = ?4, `status_id` = ?5")
    void updateCourse(Optional<String> title,Optional <String> description,Optional <Integer> credits,Optional <Integer> enrollmentLimit,Optional <Integer> statusId);

    List<Course> findByTeacherId(int teacherId);
}
