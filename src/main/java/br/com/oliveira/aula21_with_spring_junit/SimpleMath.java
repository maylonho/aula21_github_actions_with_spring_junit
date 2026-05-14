package br.com.oliveira.aula21_with_spring_junit;

public class SimpleMath {

    public double soma(double a, double b) {
        return a + b;
    }

    public double subtracao(double a, double b) {
        return a - b;
    }

    public double multiplicacao(double a, double b) {
        return a * b;
    }

    public double divisao(double a, double b) {

        if (b == 0) {
            throw new IllegalArgumentException("Não é possível dividir por zero");
        }

        return a / b;
    }

    public double potencia(double a, double b) {
        return Math.pow(a, b);
    }

    public double raizQuadrada(double a) {

        if (a < 0) {
            throw new IllegalArgumentException("Não é possível calcular raiz de número negativo");
        }

        return Math.sqrt(a);
    }

    public double media(double a, double b) {
        return (a + b) / 2;
    }

    public double restoDivisao(double a, double b) {

        if (b == 0) {
            throw new IllegalArgumentException("Não é possível calcular resto com divisor zero");
        }

        return a % b;
    }
}