package com.maveric.demo.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.maveric.demo.constant.UserConstant;
import com.maveric.demo.dto.UserDto;
import com.maveric.demo.enums.BloodGroup;
import com.maveric.demo.enums.Gender;
import com.maveric.demo.model.User;
import com.maveric.demo.service.UserService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.maveric.demo.enums.BloodGroup.B_POS;
import static com.maveric.demo.enums.Gender.FEMALE;
import static com.maveric.demo.enums.Gender.MALE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@WebMvcTest(UserController.class)
public class UserControllerTest {

    @MockBean
    UserService userService;

    @Autowired
    MockMvc mockMvc;


    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @Test
    void testGetUsers() throws Exception {
        List<UserDto> userDto = createUsersList();
        Mockito.when(userService.getAllUsers(1,10)).thenReturn(userDto);
        mockMvc.perform(get("/api/v1/users?page=1&pageSize=10"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(2)))
                .andExpect(jsonPath("$[0].firstName", Matchers.is("firstTest")));
    }

    private List<UserDto> createUsersList() {

        List<UserDto> userDto = new ArrayList<>();

        UserDto c1 = new UserDto();
        c1.setUserId("123");
        c1.setFirstName("firstTest");
        c1.setMiddleName("middleTest");
        c1.setLastName("lastTest");
        c1.setPhoneNumber("1234567890");
        c1.setDateOfBirth(LocalDate.now());
        c1.setGender(MALE);
        c1.setAddress("Bangalore");
        c1.setEmployeeId("123");
        c1.setBloodGroup(B_POS);
        c1.setEmail("aug@gmail.com");
        userDto.add(c1);

        UserDto c2 = new UserDto();
        c2.setUserId("1234");
        c2.setFirstName("firstTest2");
        c2.setMiddleName("middleTest2");
        c2.setLastName("lastTest2");
        c2.setPhoneNumber("1234567890");
        c2.setDateOfBirth(LocalDate.now());
        c2.setGender(FEMALE);

        c2.setEmployeeId("123");
        c2.setBloodGroup(B_POS);
        c2.setEmail("aug@gmail.com");
        c2.setAddress("Bangalore");

        userDto.add(c2);

        return userDto;
    }



    @Test
    void testCreateUser() throws Exception {
        User user = createOneUserDataToPost();
        //UserDto userDto=new UserDto("123","firstTest","middleTest","lastTest","123",LocalDate.now(),FEMALE,"123",B_POS,"aug@gmail.com","Bangalore");
        UserDto userDto= createUserDto();
        Mockito.when(userService.createUser(user)).thenReturn(userDto);
        mockMvc.perform(post("/api/v1/users")
                        .content(asJsonString(user))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    private UserDto createUserDto() {
        UserDto c1 = new UserDto();

        c1.setUserId("123");
        c1.setFirstName("firstTest");
        c1.setMiddleName("middleTest");
        c1.setLastName("lastTest");
        c1.setPhoneNumber("1234567890");
        //c1.setDateOfBirth(LocalDate.of(1997, 03, 23));
        c1.setGender(FEMALE);
        c1.setEmployeeId("123");
        c1.setBloodGroup(B_POS);
        c1.setEmail("aug@gmail.com");
        c1.setAddress("Bangalore");
//        c1.setPassword("123");
//        c1.setIsActive(true);

        return c1;
    }

    private User createOneUserDataToPost() {

        User c1 = new User();
        c1.setUserId("123");
        c1.setFirstName("firstTest");
        c1.setMiddleName("middleTest");
        c1.setLastName("lastTest");
        c1.setPhoneNumber("1234567890");

        c1.setGender(FEMALE);
        c1.setEmployeeId("123");
        c1.setBloodGroup(B_POS);
        c1.setEmail("aug@gmail.com");
        c1.setAddress("Bangalore");
        c1.setPassword("123");

        return c1;

    }

    @Test
    public void testGetUserDetails() throws Exception {
        UserDto userAllData = createOneUser();
        Mockito.when(userService.getUserDetails("123")).thenReturn(userAllData); //Mockito.when(userService.customerIsActive(1)).thenReturn(true); mockMvc.perform(get("/users/123"))
        mockMvc.perform(get("/api/v1/users/123"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.aMapWithSize(11)))
                .andExpect(jsonPath("$.firstName", Matchers.is("firstTest")));

    }

    private UserDto createOneUser() {

        UserDto c1 = new UserDto();
        c1.setUserId("123");
        c1.setFirstName("firstTest");
        c1.setMiddleName("middleTest");
        c1.setLastName("lastTest");
        c1.setPhoneNumber("1234567890");
        c1.setDateOfBirth(LocalDate.of(1997, 03, 23));
        c1.setGender(FEMALE);
        c1.setEmployeeId("123");
        c1.setBloodGroup(B_POS);
        c1.setEmail("aug@gmail.com");
        c1.setAddress("Bangalore");
        return c1;
    }

    @Test
    public void testUpdateUser() throws Exception {
        User user =createOneUserToPost();
        UserDto userDto = createOneUserUpdate();
      //  UserRequest userRequest = new UserRequest();
        Mockito.when(userService.updateUser(user, "1")).thenReturn(userDto);
        mockMvc.perform(put("/api/v1/users/1")
                        .content(asJsonString(user))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    private User createOneUserToPost() {
        User c1 = new User();
        c1.setUserId("1");
        c1.setFirstName("firstTest");
        c1.setMiddleName("middleTest");
        c1.setLastName("lastTest");
        c1.setPhoneNumber("1234567890");

        c1.setGender(FEMALE);
        c1.setEmployeeId("123");
        c1.setBloodGroup(B_POS);
        c1.setEmail("aug@gmail.com");
        c1.setAddress("Bangalore");
        c1.setPassword("123");

        return c1;

    }
    private UserDto createOneUserUpdate() {

        UserDto c1 = new UserDto();
        c1.setUserId("1");
        c1.setFirstName("firstTest");
        c1.setMiddleName("middleTest");
        c1.setLastName("lastTest");
        c1.setPhoneNumber("1234567890");
        c1.setDateOfBirth(LocalDate.of(1997, 03, 23));
        c1.setGender(FEMALE);
        c1.setEmployeeId("123");
        c1.setBloodGroup(B_POS);
        c1.setEmail("aug@gmail.com");
        c1.setAddress("Bangalore");
        return c1;
    }
    @Test
    public void testDeleteUser() throws Exception {

        Mockito.when(userService.deleteUser("1")).thenReturn(UserConstant.DELETEUSER);

        this.mockMvc.perform(MockMvcRequestBuilders
                        .delete("/api/v1/users/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

    }
}




