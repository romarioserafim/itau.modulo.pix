package com.modulo.pix.utils;

import org.springframework.stereotype.Component;

@Component
public class Helpers {
    public static boolean validCpf(String cpf){
        if (cpf == null || cpf.length() != 11 || cpf.matches("(\\d)\\1{10}")) {
            return false;
        }

        try {
            int soma = 0;
            for (int i = 0; i < 9; i++) {
                soma += Character.getNumericValue(cpf.charAt(i)) * (10 - i);
            }
            int primeiroDigito = (soma % 11 < 2) ? 0 : 11 - (soma % 11);

            soma = 0;
            for (int i = 0; i < 10; i++) {
                soma += Character.getNumericValue(cpf.charAt(i)) * (11 - i);
            }
            int segundoDigito = (soma % 11 < 2) ? 0 : 11 - (soma % 11);

            return cpf.charAt(9) == Character.forDigit(primeiroDigito, 10) &&
                    cpf.charAt(10) == Character.forDigit(segundoDigito, 10);

        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean validCnpj(String cnpj){
        if (cnpj == null || cnpj.length() != 14 || cnpj.matches("(\\d)\\1{13}")) {
            return false;
        }

        try {
            int[] pesos1 = {5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
            int[] pesos2 = {6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};

            int soma = 0;
            for (int i = 0; i < 12; i++) {
                soma += Character.getNumericValue(cnpj.charAt(i)) * pesos1[i];
            }
            int primeiroDigito = (soma % 11 < 2) ? 0 : 11 - (soma % 11);

            soma = 0;
            for (int i = 0; i < 13; i++) {
                soma += Character.getNumericValue(cnpj.charAt(i)) * pesos2[i];
            }
            int segundoDigito = (soma % 11 < 2) ? 0 : 11 - (soma % 11);

            return cnpj.charAt(12) == Character.forDigit(primeiroDigito, 10) &&
                    cnpj.charAt(13) == Character.forDigit(segundoDigito, 10);

        } catch (NumberFormatException e) {
            return false;
        }
    }
}
