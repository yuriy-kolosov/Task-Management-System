package pro.sky.tms_app.repository;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import pro.sky.tms_app.entity.TaskEntity;

import java.util.List;

public interface TaskRepository extends JpaRepository<TaskEntity, Long> {

    default List<TaskEntity> findPages(PageRequest taskPages) {
        return findAll(taskPages).getContent();

    }

}
