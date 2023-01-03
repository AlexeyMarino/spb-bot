package com.alexeymarino.botserver.repository;

import com.alexeymarino.botserver.domain.UserEntity;
import java.util.Set;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<UserEntity, Long> {
    @Query("select usr.userId from UserEntity usr")
    Set<Long> getAllIds();
}
