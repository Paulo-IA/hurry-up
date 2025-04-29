package com.paulo.hurry_up.exceptions;

public class EventNotFoundException extends RuntimeException {

    public EventNotFoundException() { super("Evento não encontrado!"); }
    public EventNotFoundException(String message) { super(message); }
}
