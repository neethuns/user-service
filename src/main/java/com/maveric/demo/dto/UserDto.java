package com.maveric.demo.dto;


import com.maveric.demo.enums.BloodGroup;
import com.maveric.demo.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.time.LocalDate;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class UserDto {

    @Id
    private String userId;
    private String firstName;
    private String middleName;
    private String lastName;
    private String phoneNumber;
    private LocalDate dateOfBirth;
    private Gender gender;
    private String employeeId;
    private BloodGroup bloodGroup;
    private String email;
    private String address;



}
