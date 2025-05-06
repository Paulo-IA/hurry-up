package com.paulo.hurry_up.exceptions;


public class InvalidEventFileExtensionException extends RuntimeException {
    public InvalidEventFileExtensionException() { super("Extensão do arquivo não informada!"); }
    public InvalidEventFileExtensionException(String message) { super(message); }
}
