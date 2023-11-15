package com.example.xml;

/**
 * CustomXMLParsingException représente une exception spécifique aux erreurs de parsing XML.
 * Cette exception étend la classe d'exception de base de Java.
 */
public class CustomXMLParsingException extends Exception {
    public CustomXMLParsingException(String message) {
        super(message);
    }

    public CustomXMLParsingException(String message, Throwable cause) {
        super(message, cause);
    }
}
