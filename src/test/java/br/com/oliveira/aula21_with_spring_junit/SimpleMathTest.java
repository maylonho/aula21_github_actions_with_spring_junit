package br.com.oliveira.aula21_with_spring_junit;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SimpleMathTest {

    private final SimpleMath math = new SimpleMath();

    @Test
    void deveSomarDoisNumeros() {

        double resultado = math.soma(10, 5);

        assertEquals(15, resultado);
    }

    @Test
    void deveSubtrairDoisNumeros() {

        double resultado = math.subtracao(10, 5);

        assertEquals(5, resultado);
    }

    @Test
    void deveMultiplicarDoisNumeros() {

        double resultado = math.multiplicacao(10, 5);

        assertEquals(50, resultado);
    }

    @Test
    void deveDividirDoisNumeros() {

        double resultado = math.divisao(10, 2);

        assertEquals(5, resultado);
    }

    @Test
    void naoDeveDividirPorZero() {

        Exception exception = assertThrows(
                IllegalArgumentException.class,
                () -> math.divisao(10, 0)
        );

        assertEquals(
                "Não é possível dividir por zero",
                exception.getMessage()
        );
    }

    @Test
    void deveCalcularPotencia() {

        double resultado = math.potencia(2, 3);

        assertEquals(8, resultado);
    }

    @Test
    void deveCalcularRaizQuadrada() {

        double resultado = math.raizQuadrada(25);

        assertEquals(5, resultado);
    }

    @Test
    void naoDeveCalcularRaizNegativa() {

        Exception exception = assertThrows(
                IllegalArgumentException.class,
                () -> math.raizQuadrada(-1)
        );

        assertEquals(
                "Não é possível calcular raiz de número negativo",
                exception.getMessage()
        );
    }

    @Test
    void deveCalcularMedia() {

        double resultado = math.media(8, 6);

        assertEquals(7, resultado);
    }

    @Test
    void deveCalcularRestoDaDivisao() {

        double resultado = math.restoDivisao(10, 3);

        assertEquals(1, resultado);
    }

    @Test
    void naoDeveCalcularRestoComDivisorZero() {

        Exception exception = assertThrows(
                IllegalArgumentException.class,
                () -> math.restoDivisao(10, 0)
        );

        assertEquals(
                "Não é possível calcular resto com divisor zero",
                exception.getMessage()
        );
    }
}