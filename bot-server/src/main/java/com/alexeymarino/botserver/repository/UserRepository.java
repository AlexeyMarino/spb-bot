package com.alexeymarino.botserver.repository;

import com.alexeymarino.botserver.domain.User;
import com.alexeymarino.botserver.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<UserEntity, Long> {

}
