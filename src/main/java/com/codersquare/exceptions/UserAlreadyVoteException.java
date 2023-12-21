package com.codersquare.exceptions;

public class UserAlreadyVoteException extends RuntimeException {
    public UserAlreadyVoteException(String message) {
        super(message);
    }
}
