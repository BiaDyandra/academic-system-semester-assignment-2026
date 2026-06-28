package org.example.academic.system.controller.input;

public interface MenuInput {

    String readText(String prompt);

    double readDouble(String prompt);

    int readInt(String s);
}
