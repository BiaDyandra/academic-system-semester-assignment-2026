package org.example.academic.system.view;

import org.example.academic.system.controller.AcademicSystemController;
import org.example.academic.system.controller.MenuOption;
import org.example.academic.system.exception.AcademicDomainException;
import org.example.academic.system.exception.AuthenticationException;
import org.example.academic.system.exception.InvalidKeyboardInputException;
import org.example.academic.system.exception.AuthorizationException;
import org.example.academic.system.exception.KeyboardInputException;
import org.example.academic.system.security.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CommandLineMenu {

    private final AcademicSystemController controller;
    private final Scanner scanner;
    private final KeyboardInputReader inputReader;

    private List<MenuOption> currentOptions;

    public CommandLineMenu(AcademicSystemController controller) {
        this.controller = controller;
        this.scanner = new Scanner(System.in);
        this.inputReader = new KeyboardInputReader(scanner);
        this.currentOptions = new ArrayList<>();
    }

    public void show() {
        while (true) {
            authenticateUser();

            MenuOption option = MenuOption.INVALID;

            do {
                try {
                    printMenu();
                    option = readMenuOption();
                    execute(option);
                } catch (KeyboardInputException e) {
                    System.out.println("Erro de entrada: " + e.getMessage());
                }

                if (option == MenuOption.LOGOUT) {
                    break;
                }
            } while (option != MenuOption.EXIT);

            if (option == MenuOption.EXIT) {
                break;
            }
        }
    }

    private void authenticateUser() {
        boolean authenticated = false;

        while (!authenticated) {
            System.out.println();
            System.out.println("===== Login =====");
            System.out.println("Usuarios de teste: admin/admin123 ou professor/prof123");

            String username = inputReader.readText("Usuario: ");
            String password = inputReader.readText("Senha: ");

            try {
                System.out.println(controller.login(username, password));
                authenticated = true;
            } catch (AuthenticationException e) {
                System.out.println("Erro de autenticacao: " + e.getMessage());
            }
        }
    }

    private void printMenu() {
        System.out.println();
        System.out.println("===== Menu Principal =====");
        System.out.println("Usuario logado: " + controller.getLoggedUserDescription());
        System.out.println("Tipo de persistencia: " + controller.getCurrentPersistenceTypeDescription());

        currentOptions.clear();
        User currentUser = controller.getAuthenticationService().getAuthenticatedUser();

        int counter = 1;
        for (MenuOption option : MenuOption.values()) {
            if (option.isAllowedFor(currentUser.getRole())) {
                currentOptions.add(option);
                System.out.println(counter + " - " + option.getDescription());
                counter++;
            }
        }
    }

    private MenuOption readMenuOption() {
        int optionCode = inputReader.readInt("Escolha uma opcao: ");
        if (optionCode < 1 || optionCode > currentOptions.size()) {
            throw new InvalidKeyboardInputException("Opcao de menu invalida: " + optionCode + ".");
        }
        return currentOptions.get(optionCode - 1);
    }

    private void execute(MenuOption option) {
        try {
            System.out.println(controller.executeMenuOption(option, inputReader));
        } catch (AuthorizationException e) {
            System.out.println("Erro de autorizacao: " + e.getMessage());
        } catch (AuthenticationException e) {
            System.out.println("Erro de autenticacao: " + e.getMessage());
        } catch (AcademicDomainException | KeyboardInputException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }
}
