package com.maveric.demo.model;


import com.maveric.demo.enums.BloodGroup;
import com.maveric.demo.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import javax.validation.constraints.*;


@Document(collection="user")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {


        @Id
        private String userId;
        @NotNull(message="First Name can not be null")
        @Size(min=3,max=50,message = "First Name size must be between 3 and 50")
        private String firstName;
        private String middleName;
        @NotNull(message="Last Name can not be Null")
        @Size(min=3,max=50,message = "Last Name size must be between 3 and 50")
        private String lastName;
        @NotNull(message = "Phone Number is required")
        @Size(min=10,max=16)
        @Pattern(regexp="(^$|[0-9]{10})",message = "Phone number must be between 0-9")
        private String phoneNumber;
        @NotNull(message="Email can not be null")
        @Email(message = "Check the EmailId ")
        private String email;
        @NotNull(message="Address can not be null")
        private String address;

        private LocalDate dateOfBirth;
        @NotNull(message="Employee ID can not be null")
        private String employeeId;
        private BloodGroup bloodGroup;
        private Gender gender;
        @NotNull(message="Password can not be null")
        private String password;


}
