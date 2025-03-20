package com.eddy.chatapp.core;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class loginTest {

    @Test
    void registro() {
        Login login = new Login();
        assertTrue(login.registro("12345"));
    }

    @Test
    void login() {
        Login login = new Login();
        assertTrue(login.login("12345"));
    }

    @Test
    void existeContra() {
        Login login = new Login();
        boolean result = login.existeContra();
        assertTrue(result);
    }
}