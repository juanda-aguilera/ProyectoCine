package com.gitb.juandaaguilera.Sistema.modelo;

import java.time.format.DateTimeFormatter;
import java.util.Locale;

public final class Formato {
    private static final Locale COLOMBIA = Locale.of("es", "CO");
    public static final DateTimeFormatter FECHA_HORA = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    private Formato() {
    }

    public static String moneda(double valor) {
        return String.format(COLOMBIA, "$%,.0f", valor);
    }
}
