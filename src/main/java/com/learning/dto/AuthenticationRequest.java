package com.learning.dto;
public record AuthenticationRequest(
        String username,
        String password
){
}