package com.booking.exception;

public class HallException extends RuntimeException {
    
    public HallException(String message) {
        super(message);
    }

    public static class HallExceptionNotFound extends HallException {
        
        public HallExceptionNotFound(String message) {
            super(message);
        }
    } 
    
}