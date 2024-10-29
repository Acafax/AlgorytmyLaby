package org.example;

import java.util.Objects;

public class PlecakKlasa{
    private int wartosc;
    private int waga;


    public PlecakKlasa( int wartosc, int waga) {
        this.wartosc = wartosc;
        this.waga = waga;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlecakKlasa that = (PlecakKlasa) o;
        return waga == that.waga && wartosc == that.wartosc;
    }

    @Override
    public int hashCode() {
        return Objects.hash(waga, wartosc);
    }

    public int getWaga() {
        return waga;
    }

    public void setWaga(int waga) {
        this.waga = waga;
    }

    public int getWartosc() {
        return wartosc;
    }

    public void setWartosc(int wartosc) {
        this.wartosc = wartosc;
    }
}
