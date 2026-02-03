package model.entity;

import java.util.Optional;

/**
 * Provjerava postojanje nekog rezultata pretrage
 * @param <T> tip rezultata koji ocekujemo ako rezultat postoji
 * @param <E> greska ako se ne nade rezultat
 */

public class Rezultat <T,E>{
    private T value;
    private E error;

    public Rezultat(T value, E error) {
        this.value = value;
        this.error = error;
    }

    /**
     * Vraca novi objekt kad rezultat postoji
     * @param t sto sse dohvaca kad rezultat postoji
     * @return novi objekt rezultata
     * @param <T> tip koji pretrazujemo
     * @param <E> greska ako se ne nade rezultat
     */

    public static<T,E> Rezultat<T,E> rezultatOK(T t){
        return new Rezultat<T,E>(t, null);
    }

    /**
     * Vraca novi objekt(rezultat) ili objekt sa greskom kada rezultat ne postoji
     * @param e greska koja se zabiljezava
     * @return novi objekt nepostojeceg rezultata
     * @param <T> tip koji se pretrazuja
     * @param <E> greska ako se ne nade rezultat
     */

    public static<T,E> Rezultat<T,E> rezultatNotOK(E e){
        return new Rezultat<T,E>(null,e);
    }

    @Override
    public String toString(){
        return "Rezultat{" + "value=" + value + ", error=" + error + '}';
    }

    public Optional<T> getValue(){
        return Optional.ofNullable(value);
    }
    public Optional<E> getError(){
        return Optional.ofNullable(error);
    }
    public boolean isOk(){
        return value != null;
    }
}
