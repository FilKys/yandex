package ru.fil.prognoz;

import java.util.Scanner;

public class Prognoz {

    public void work() {
        try {
            double hour, road1, road2, road3, road4, road5, newRoad, a, t2, v;
            hour = getHour();
            newRoad = getMetr();
            v = 626.144;
            a = getA(hour) + 1.56622;
            t2 = Math.pow((hour - 0.375), 2) / (2 * 0.006944);
            System.out.println("Time = " + hour);
            road1 =  Math.exp(0.966 * a + 1.253 * t2);
            road2 =  Math.exp(0.989 * a + 0.697 * t2);
            road3 =  Math.exp(1.004 * a + 1.610 * t2);
            road4 =  Math.exp(1.004 * a + 0.719 * t2);
            road5 =  Math.exp(0.997 * a + 0.721 * t2);
            System.out.println("Road1 (7.7 км) = (м/мин) " +(v-road1));
            System.out.println("Road2 (9.6 км) = (м/мин) " + (v-road2));
            System.out.println("Road3 (11 км) = (м/мин) " + (v-road3));
            System.out.println("Road4 (10 км) = (м/мин) " + (v-road4));
            System.out.println("Road5 (21 км) = (м/мин) " + (v-road5));
            newRoad = (newRoad / (v-(road1 + road2 + road3 + road4 + road5) / 5));
            System.out.println("Время в пути составит примерно = " + newRoad + " минут");
        } catch (Exception e) {
            System.out.println(" ");
            e.printStackTrace();
        }
    }

    private double getA(double hour) {
        double k8 = 3.941;
        double k9 = 4.039;
        double k10 = 3.810;
        if (hour == ((double) 8 / 24)) {
            return k8;
        } else if (hour > ((double) 8 / 24) && hour < ((double) 9 / 24)) {
            hour -= (double) 8 / 24;
            return (k9 - k8) * hour + k8;
        } else if (hour == ((double) 9 / 24)) {
            return k9;
        } else if (hour > ((double) 9 / 24) && hour < ((double) 10 / 24)) {
            hour -= (double) 9 / 24;
            return (k9 - k10) * hour + k10;
        } else {
            return k10;
        }
    }

    private float getMetr() {
        Scanner in = new Scanner(System.in);
        System.out.print("Введите продолжительность пути в МТЕРАХ : ");
        return in.nextFloat();
    }

    private double getHour() throws Exception {
        double time, a;
        Scanner in = new Scanner(System.in);
        System.out.print("На который час рассчитать время в пути?(целое значение часа 8-10): ");
        a = in.nextFloat();
        if (a >= 8 || a <= 10) {
            time = a / 24;
        } else {
            throw new Exception("Время в часах должно быть <= 10 и >= 8!");
        }
        System.out.print("На которую минуту рассчитать время в пути?(целое значение минут 0-60): ");
        a = in.nextFloat();
        if (a >= 0 && a < 60) {
            time += a / 60 / 24;
        } else {
            throw new Exception("Минуты должно быть меньше 60 и больше или равна 0!");
        }
        return time;
    }

}
