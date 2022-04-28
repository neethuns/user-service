package com.maveric.demo.repo;

import com.maveric.demo.dto.UserDto;
import com.maveric.demo.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends MongoRepository<User, String>{

    UserDto findByUserId(String userId);

    UserDto findByEmail(String email);
}
