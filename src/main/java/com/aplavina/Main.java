package com.aplavina;

import java.util.function.Function;

import static com.aplavina.RootFindingAlgorithms.*;

public class Main {
    public static void main(String[] args) {
        Function<Double, Double> function = x -> x * x - 3 * x + x * Math.log(x);
        double a = 1;
        double b = 2;
        double epsilon = 0.05;
        try {
            System.out.println("Bisection method: ");
            Answer answerBisect = RootFindingAlgorithms.bisection(function, a, b, epsilon);
            System.out.println("xm = " + answerBisect.getXm());
            System.out.println("ym = " + answerBisect.getYm());

            System.out.println("Golden section method: ");
            Answer answerGolden = RootFindingAlgorithms.goldenSection(function, a, b, epsilon);
            System.out.println("xm = " + answerGolden.getXm());
            System.out.println("ym = " + answerGolden.getYm());

            System.out.println("Chord method: ");
            Answer answerChord = RootFindingAlgorithms.chord(function, a, b, epsilon);
            System.out.println("xm = " + answerChord.getXm());
            System.out.println("ym = " + answerChord.getYm());


        } catch (IllegalArgumentException ex) {
            System.out.println(ex.getMessage());
        }
    }
}