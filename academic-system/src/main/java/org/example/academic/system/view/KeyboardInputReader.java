package org.example.academic.system.view;

import org.example.academic.system.exception.InvalidKeyboardInputException;
import org.example.academic.system.controller.input.MenuInput;

import java.util.Scanner;

public class KeyboardInputReader implements MenuInput {

    private final Scanner scanner;

    public KeyboardInputReader(Scanner scanner) {
        this.scanner = scanner;
    }

    public String readText(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    public double readDouble(String prompt) {
        System.out.print(prompt);
        String value = scanner.nextLine().replace(',', '.');

        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            throw new InvalidKeyboardInputException("Entrada invalida. Informe um numero valido.");
        }
    }

    public int readInt(String prompt) {
        System.out.print(prompt);
        String value = scanner.nextLine();

        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            throw new InvalidKeyboardInputException("Entrada invalida. Informe uma opcao numerica.");
        }
    }
}
