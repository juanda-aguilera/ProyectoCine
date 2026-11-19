package com.gitb.juandaaguilera.Sistema.interfaz;

import com.gitb.juandaaguilera.Sistema.modelo.Describible;
import java.util.List;

public class Combos {
    public static String[] textos(List<? extends Describible> lista) {
        String[] textos = new String[lista.size()];
        for (int i = 0; i < lista.size(); i++) {
            textos[i] = lista.get(i).getDescripcion();
        }
        return textos;
    }
}
