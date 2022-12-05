package com.alexeymarino.botserver.repository;

import com.alexeymarino.botserver.domain.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
}
