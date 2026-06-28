package org.example.academic.system.repository;

import org.example.academic.system.exception.InvalidKeyboardInputException;

import java.util.Arrays;

public enum PersistenceType {

    TXT(1, "TXT"),
    XML(2, "XML"),
    JSON(3, "JSON");

    private final int code;
    private final String description;

    PersistenceType(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public static PersistenceType fromCode(int code) {
        return Arrays.stream(values())
                .filter(type -> type.code == code)
                .findFirst()
                .orElseThrow(() -> new InvalidKeyboardInputException("Tipo de persistencia invalido: " + code + "."));
    }

    public static String menuOptionsText() {
        StringBuilder builder = new StringBuilder();

        for (PersistenceType type : values()) {
            builder.append(type.getCode())
                    .append(" - ")
                    .append(type.getDescription())
                    .append(System.lineSeparator());
        }

        return builder.toString();
    }
}
