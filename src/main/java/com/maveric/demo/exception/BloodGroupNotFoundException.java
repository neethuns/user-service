package com.maveric.demo.exception;

public class BloodGroupNotFoundException extends RuntimeException{
    public BloodGroupNotFoundException(String s) {
        super(s);
    }
}
