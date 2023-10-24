package com.example.xml;

class CustomXMLParsingException extends Exception {
    public CustomXMLParsingException(String message) {
        super(message);
    }

    public CustomXMLParsingException(String message, Throwable cause) {
        super(message, cause);
    }
}
