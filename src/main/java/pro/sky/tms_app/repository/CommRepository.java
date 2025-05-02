package pro.sky.tms_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pro.sky.tms_app.entity.CommEntity;

public interface CommRepository extends JpaRepository<CommEntity, Long> {

}
