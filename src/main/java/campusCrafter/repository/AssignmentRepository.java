package campusCrafter.repository;

import campusCrafter.model.Assignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface AssignmentRepository extends JpaRepository<Assignment, Integer> {

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "update `assignment` set `title` = ?1, `content` = ?2, `due_date` = ?3, `max_score` = ?4, `submission_format` = ?5 where id = ?6")
    void updateAssignment(Optional<String> title, Optional<String> content, Optional<String> dueDate, Optional<Integer> maxScore, Optional<String> submissionFormat, int id);

    Assignment findById(int id);

    List<Assignment> findByCourseId(int courseId);


}
