package com.gitb.juandaaguilera.Sistema;

import java.awt.EventQueue;

public class SistemaCine {

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            Registros registros = new Registros();
            new VentanaMenu(registros).setVisible(true);
        });
    }
}
