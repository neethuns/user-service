package com.maveric.demo.repo;


import com.maveric.demo.dto.UserDto;
import com.maveric.demo.enums.BloodGroup;
import com.maveric.demo.enums.Gender;
import com.maveric.demo.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
@DataMongoTest
public class UserRepoTest {

    @Autowired
    UserRepo userRepo;
    @BeforeEach
    void initUseCase() {
        User user = createUser();
        userRepo.save(user);
    }

    @AfterEach
    public void destroyByAll() {
        userRepo.deleteAll();
    }


    @Test
    public void findByUserId() {
        UserDto user = userRepo.findByUserId("1");
        assertEquals("1", user.getUserId());
    }

    @Test
    public void findByEmail() {
        UserDto user = userRepo.findByEmail(createUser().getEmail());
        assertEquals("augus@mail.com", user.getEmail());
    }

    private User createUser() {

        User user = new User();
        user.setUserId("1");
        user.setFirstName("firstTest");
        user.setMiddleName("J");
        user.setLastName("S");
        user.setPhoneNumber("9090909090");
        user.setEmail("augus@mail.com");
        user.setDateOfBirth(LocalDate.of(1997, 03, 23));
        user.setEmployeeId("12345");
        user.setBloodGroup(BloodGroup.O_POS);
        user.setGender(Gender.MALE);
        user.setPassword("1234");
        return user;
    }

}
