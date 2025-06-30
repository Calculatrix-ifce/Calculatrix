package com.calculatrix.calculatrixlogin;

import javax.swing.JButton;

public class Util {
    public static boolean verificarVitoria(JButton[][] botoes) {
        for (int i = 0; i < 3; i++) {
            if (igual(botoes[i][0], botoes[i][1], botoes[i][2])) return true;
            if (igual(botoes[0][i], botoes[1][i], botoes[2][i])) return true;
        }
        if (igual(botoes[0][0], botoes[1][1], botoes[2][2])) return true;
        if (igual(botoes[0][2], botoes[1][1], botoes[2][0])) return true;

        return false;
    }

    public static boolean empate(JButton[][] botoes) {
        for (JButton[] linha : botoes) {
            for (JButton botao : linha) {
                if (botao.getText().equals("")) return false;
            }
        }
        return true;
    }

    private static boolean igual(JButton b1, JButton b2, JButton b3) {
        return !b1.getText().equals("") &&
               b1.getText().equals(b2.getText()) &&
               b2.getText().equals(b3.getText());
    }
}