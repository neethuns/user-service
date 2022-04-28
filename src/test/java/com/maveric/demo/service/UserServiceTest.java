package com.maveric.demo.service;


import com.maveric.demo.constant.UserConstant;
import com.maveric.demo.dto.UserDto;
import com.maveric.demo.enums.BloodGroup;
import com.maveric.demo.enums.Gender;
import com.maveric.demo.exception.UserNotFoundException;
import com.maveric.demo.model.User;
import com.maveric.demo.repo.UserRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.*;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.junit.jupiter.api.Assertions.*;

import org.springframework.data.domain.PageRequest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest

public class UserServiceTest {
    @InjectMocks
    UserServiceImpl service;
    @Mock
    UserRepo userRepo;

    @Test
    public void TestCreateUser() {

        UserDto userDto = createOneUserToResponse();
        User user = createOneUserToPost();
        when(userRepo.save(user)).thenReturn(user);
        User savedUser = userRepo.save(user);
        when(service.createUser(user)).thenReturn(userDto);
        //assertThat(savedUser.getFirstName()).isNotNull();
        assertEquals(savedUser.getEmail(), "augus@mail.com");
        //assertThrows(EmailAlreadyExistException.class, () -> service.createUser(userRequest));
    }

    //    @Test
//    public void TestCreateUserThrowException() {
//
//       User user= createOneUserToPost();
//        /*when(userRepo.findByEmail(userRequest.getEmail()).isPresent());
//        assertThrows(EmailAlreadyExistException.class, () -> service.createUser(userRequest));*/
//
//        Mockito.doThrow(UserNotFoundException.class).when(userRepo).findByEmail("prabhu@mail.com");
//        Exception emailAlreadyExistException = assertThrows((UserNotFoundException.class), () -> service.createUser(user));
//        assertTrue(emailAlreadyExistException.getMessage().contains(UserConstant.USEREMAILEXISTS));
//
//    }
    private UserDto createOneUserToResponse() {
        UserDto userDto = new UserDto();
        userDto.setUserId("1");
        userDto.setFirstName("firstTest");
        userDto.setMiddleName("J");
        userDto.setLastName("S");
        userDto.setPhoneNumber("9090909090");
        userDto.setEmail("augus@mail.com");
        userDto.setDateOfBirth(LocalDate.of(1997, 03, 23));
        userDto.setEmployeeId("12345");
        userDto.setBloodGroup(BloodGroup.O_POS);
        userDto.setGender(Gender.MALE);
        return userDto;
    }

    private User createOneUserToPost() {
        User user = new User();
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
        @Test
        public void TestDeleteUser(){

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
            Mockito.when(userRepo.findById("1")).thenReturn(Optional.of(user));

           service.deleteUser("1");
            verify(userRepo, times(1)).deleteById("1");


        }
    @Test
    public void TestDeleteUserByIdThrowException() {
        Mockito.doThrow(UserNotFoundException.class).when(userRepo).deleteById(any());
        Exception userNotFoundException = assertThrows(UserNotFoundException.class, () -> service.deleteUser("1"));
        assertTrue(userNotFoundException.getMessage().contains(UserConstant.USERNOTFOUND));
    }

    @Test
    public void testUpdateUser() {
        Optional<User> userSelected = getOneUser();
        UserDto userDto = new UserDto();
        userDto.setUserId("1");
        userDto.setFirstName("firstTest");
        userDto.setMiddleName("J");
        userDto.setLastName("S");
        userDto.setPhoneNumber("9090345678");
        userDto.setEmail("augus@mail.com");
        userDto.setDateOfBirth(LocalDate.of(1997, 03, 23));
        userDto.setEmployeeId("12345");
        userDto.setBloodGroup(BloodGroup.O_POS);
        userDto.setGender(Gender.MALE);
        User user = createOneUser();
        Mockito.when(userRepo.findById("1")).thenReturn(userSelected);
        user.setPhoneNumber("9090345678");
        Mockito.when(userRepo.save(user)).thenReturn(user);
        assertThat(service.updateUser(user, "1")).isEqualTo(userDto);

    }
    private User createOneUser() {
        User user = new User();
        user.setUserId("1");
        user.setFirstName("firstTest");
        user.setMiddleName("J");
        user.setLastName("S");
        user.setPhoneNumber("9090345678");
        user.setEmail("augus@mail.com");
        user.setDateOfBirth(LocalDate.of(1997, 03, 23));
        user.setEmployeeId("12345");
        user.setBloodGroup(BloodGroup.O_POS);
        user.setGender(Gender.MALE);
        user.setPassword("1234");
        return user;
    }
    @Test
    public void testGetUserDetails() {
       // Optional<User> userSelected = getOneUser();
        UserDto userDto = createOneUserToResponse();
        //User user = createOneUserToPost();
        Mockito.when(userRepo.findByUserId("1")).thenReturn(userDto);
        assertThat(service.getUserDetails("1")).isEqualTo(userDto);

    }

    private Optional<User> getOneUser() {
        Optional<User> user = Optional.of(new User("1", "firstTest", "J", "S", "9090909090", "augus@mail.com", "bangalore",
                LocalDate.of(1997, 03, 23), "12345", BloodGroup.O_POS,
                Gender.MALE, "1234"));
        return user;
    }

    @Test
    public void testGetUsers() {

        ArrayList<User> userList = getUserList();

        ArrayList<UserDto> userDtoList = getUserDtoList();

        PageImpl<User> users = new PageImpl<>(userList);
       Mockito.when(this.userRepo.findAll((org.springframework.data.domain.Pageable) any())).thenReturn(users);
        assertEquals(2, this.service.getAllUsers(1,3).size());
        assertThat(service.getAllUsers(1,3)).isEqualTo(userDtoList);

    }
    User RECORD_1 = new User("1", "firstTest", "J", "S", "9090909090", "augus@mail.com", "bangalore",
            LocalDate.of(1997, 03, 23), "12345", BloodGroup.O_POS,
            Gender.MALE, "1234");
    User RECORD_2 = new User("2", "secondTest", "J", "S", "9866838990", "neethu@mail.com", "bangalore",
            LocalDate.of(1997, 02, 25), "1234", BloodGroup.B_POS,
            Gender.FEMALE, "1234");


    UserDto RECORD1 = new UserDto("1", "firstTest", "J", "S", "9090909090",
            LocalDate.of(1997, 03, 23),
            Gender.MALE,  "12345", BloodGroup.O_POS,"augus@mail.com","bangalore");
    UserDto RECORD2 = new UserDto("2", "secondTest", "J", "S", "9866838990", LocalDate.of(1997, 02, 25),  Gender.FEMALE,
            "1234", BloodGroup.B_POS,"neethu@mail.com","bangalore");


    private ArrayList<User> getUserList() {
        ArrayList<User> userList = new ArrayList<>();
        userList.add(RECORD_1);
        userList.add(RECORD_2);
        return userList;
    }


    private ArrayList<UserDto> getUserDtoList() {
        ArrayList<UserDto> userDtoList = new ArrayList<>();
        userDtoList .add(RECORD1);
        userDtoList .add(RECORD2);
        return userDtoList;
    }

    @Test
    public void testUserByEmail() {
        UserDto userDto = createOneUserToResponse();
        User user = createOneUserToPost();
        Mockito.when(userRepo.findByEmail(user.getEmail())).thenReturn(userDto);
        assertThat(service.userByEmail("augus@mail.com")).isEqualTo(userDto);

    }
}