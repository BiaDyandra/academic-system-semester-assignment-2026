package org.example.academic.system.model;

import org.example.academic.system.security.Role;
import org.example.academic.system.security.User;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DomainEqualityTest {

    @Test
    void testAcademicClassEquality() {
        AcademicClass class1 = new AcademicClass("POO", "Programacao Orientada a Objetos");
        AcademicClass class2 = new AcademicClass("POO", "Programacao Orientada a Objetos 2");
        AcademicClass class3 = new AcademicClass("BD", "Banco de Dados");

        assertEquals(class1, class2);
        assertEquals(class1.hashCode(), class2.hashCode());
        assertNotEquals(class1, class3);
    }

    @Test
    void testUserEquality() {
        User user1 = new User("admin", "pass", Role.ADMIN);
        User user2 = new User("admin", "diff", Role.PROFESSOR);
        User user3 = new User("prof", "pass", Role.PROFESSOR);

        assertEquals(user1, user2);
        assertEquals(user1.hashCode(), user2.hashCode());
        assertNotEquals(user1, user3);
    }
}
