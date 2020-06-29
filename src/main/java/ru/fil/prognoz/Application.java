package ru.fil.prognoz;

import java.util.Scanner;

public class Application {

    public static void main(String[] args) {
        Prognoz prognoz = new Prognoz();
        while (true) {
            prognoz.work();
        }
    }


}
