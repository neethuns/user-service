package com.maveric.demo.service;

import com.maveric.demo.dto.UserDto;
import com.maveric.demo.enums.BloodGroup;

import com.maveric.demo.exception.UserNotFoundException;
import com.maveric.demo.model.User;
import com.maveric.demo.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.maveric.demo.constant.UserConstant.*;


@Service
public class UserServiceImpl implements UserService{

    @Autowired
    UserRepo userRepo;

    @Override
    public List<UserDto> getAllUsers(Integer page,Integer pageSize) {


    if (page == null) {
        page = 1;
    }
    if (pageSize == null) {
        pageSize = 10;
    }
    Page<User> users = userRepo.findAll(PageRequest.of(page - 1, pageSize));
    List<UserDto> userList = new ArrayList<>();
    for (User user1 : users) {
        userList.add(new UserDto(user1.getUserId(), user1.getFirstName(), user1.getMiddleName(), user1.getLastName(), user1.getPhoneNumber(), user1.getDateOfBirth(), user1.getGender(), user1.getEmployeeId(), user1.getBloodGroup(), user1.getEmail(), user1.getAddress()));
    }
    if (userList.isEmpty()) {
        throw new UserNotFoundException(USERNOTFOUND);
    }
    return userList;

}



    @Override
    public UserDto createUser(User user) {

        if (userRepo.findByEmail(user.getEmail())==null){
                userRepo.save(user);
                return new UserDto(user.getUserId(), user.getFirstName(), user.getMiddleName(), user.getLastName(), user.getPhoneNumber(), user.getDateOfBirth(), user.getGender(), user.getEmployeeId(), user.getBloodGroup(), user.getEmail(), user.getAddress());
            }

        else
        {
            throw new UserNotFoundException(USEREMAILEXISTS);
        }


          }

    @Override
    public UserDto getUserDetails(String  userId) {
        UserDto userDto = userRepo.findByUserId(userId);
        if((userDto!=null)){
            return userDto;
        }
        else{
            throw new UserNotFoundException(USERNOTFOUND + userId);
        }
    }

    @Override
    public UserDto updateUser(User user, String userId) {


        Optional<User> userSelected = userRepo.findById(userId);
        User userUpdate;
        if(userSelected.isPresent()) {
            userUpdate = userSelected.get();

            if (userId.equals(user.getUserId())) {

                if (userUpdate.getEmail().equals(user.getEmail()) || (userRepo.findByEmail(user.getEmail()) == null)) {
                    userUpdate.setFirstName(user.getFirstName());
                    userUpdate.setLastName(user.getLastName());
                    userUpdate.setMiddleName(user.getMiddleName());
                    userUpdate.setPhoneNumber(user.getPhoneNumber());
                    userUpdate.setDateOfBirth(user.getDateOfBirth());
                    userUpdate.setGender(user.getGender());
                    userUpdate.setEmployeeId(user.getEmployeeId());
                    userUpdate.setBloodGroup(user.getBloodGroup());
                    userUpdate.setPassword(user.getPassword());

                    userUpdate.setEmail(user.getEmail());

                    //save it back to repo with changes
                    userRepo.save(userUpdate);
                    return new UserDto(user.getUserId(), user.getFirstName(), user.getMiddleName(), user.getLastName(), user.getPhoneNumber(), user.getDateOfBirth(), user.getGender(), user.getEmployeeId(), user.getBloodGroup(), user.getEmail(), user.getAddress());
                } else {
                    throw new UserNotFoundException(USEREMAILEXISTS);
                }
            } else {
                throw new UserNotFoundException(USERIDMISMATCH);
            }
        }
        else
        {throw new UserNotFoundException(USERNOTFOUND + userId);}
    }
    @Override
    public String deleteUser(String userId)
    {

            Optional<User> userSelected = userRepo.findById(userId);
            if(userSelected.isPresent())
             {

            userRepo.deleteById(userId);
             return DELETEUSER + userId;
                }
       else
          {
              throw new UserNotFoundException(USERNOTFOUND + userId);
        }
    }
@Override
    public UserDto userByEmail(String email){
        if(userRepo.findByEmail(email)!=null){
            return userRepo.findByEmail(email);
        }
        else{
            throw new UserNotFoundException(USERNOTFOUND);
        }
    }

}
