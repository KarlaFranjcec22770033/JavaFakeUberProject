package model.entity;

import java.math.BigDecimal;

public class IzracunCijene<T extends Number> {
        public static <T extends Number> BigDecimal cijenaRute(T kilometraza) {
            BigDecimal km = new BigDecimal(kilometraza.toString());//zato jer postoje samo String i BigInt konstruktori, a brojevi se dobro parsaju kroz toString()
            BigDecimal pocetak = new BigDecimal("0.5");// -||-
            BigDecimal rate = new BigDecimal("1.24");// -||-

            return km.multiply(pocetak).add(km.multiply(rate));
        }
}
