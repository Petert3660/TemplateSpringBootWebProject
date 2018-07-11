package com.ptconsultancy.repositories;

import com.ptconsultancy.entities.UpdateEntity;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface UpdateEntityRepository extends CrudRepository<UpdateEntity, Long> {

    List<UpdateEntity> findByCreatedAt(LocalDateTime date);

    List<UpdateEntity> findByTags(String tags);

    List<UpdateEntity> findByUsername(String username);
}
