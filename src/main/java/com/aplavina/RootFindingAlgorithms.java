package com.aplavina;

import java.util.function.Function;

public class RootFindingAlgorithms {
    private static final double GOLDEN_RATIO_COEFFICIENT1 = 0.382;
    private static final double GOLDEN_RATIO_COEFFICIENT2 = 0.618;
    private static final double DERIVATIVE_PRECISION = 0.0000000000001;

    public static Answer bisection(Function<Double, Double> function, double a, double b, double epsilon) {
        while (b - a > 2 * epsilon) {
            double x1 = (a + b - epsilon) / 2;
            double x2 = (a + b + epsilon) / 2;
            double y1 = function.apply(x1);
            double y2 = function.apply(x2);
            if (y1 > y2) {
                a = x1;
            } else {
                b = x2;
            }
        }
        double xm = (a + b) / 2;
        double ym = function.apply(xm);
        return new Answer(xm, ym);
    }

    public static Answer goldenSection(Function<Double, Double> function, double a, double b, double epsilon) {
        double x1 = a + GOLDEN_RATIO_COEFFICIENT1 * (b - a);
        double x2 = a + GOLDEN_RATIO_COEFFICIENT2 * (b - a);
        double y1 = function.apply(x1);
        double y2 = function.apply(x2);

        while (b - a > 2 * epsilon) {
            if (y1 < y2) {
                b = x2;
                x2 = x1;
                y2 = y1;
                x1 = a + GOLDEN_RATIO_COEFFICIENT1 * (x2 - a);
                y1 = function.apply(x1);
            } else {
                a = x1;
                x1 = x2;
                x2 = a + GOLDEN_RATIO_COEFFICIENT2 * (b - x1);
                y2 = function.apply(x2);
            }
        }
        double xm = (a + b) / 2;
        double ym = function.apply(xm);
        return new Answer(xm, ym);
    }

    public static Answer chord(Function<Double, Double> function, double a, double b, double epsilon) {
        double derivativePrecision = epsilon / 2;
        double derivativeOfA = getDerivative(function, a);
        double derivativeOfB = getDerivative(function, b);
        if (derivativeOfA * derivativeOfB < 0) {
            throw new IllegalArgumentException("f'(a) and f'(b) must have different signs");
        }
        double chordInterceptPoint = a - (((derivativeOfA) / (derivativeOfA - derivativeOfB)) * (a - b));
        double derivativeOfChordInterceptPoint = getDerivative(function, chordInterceptPoint);
        while (Math.abs(derivativeOfChordInterceptPoint) > epsilon) {
            if (derivativeOfA * derivativeOfChordInterceptPoint < 0) {
                b = chordInterceptPoint;
                derivativeOfB = derivativeOfChordInterceptPoint;
            } else {
                a = chordInterceptPoint;
                derivativeOfA = derivativeOfChordInterceptPoint;
            }
            chordInterceptPoint = a - (derivativeOfA) / (derivativeOfA - derivativeOfB) * (a - b);
            derivativeOfChordInterceptPoint = getDerivative(function, chordInterceptPoint);
        }
        return new Answer(chordInterceptPoint, function.apply(chordInterceptPoint));
    }

    private static double getDerivative(Function<Double, Double> function, double x) {
        return (function.apply(x + DERIVATIVE_PRECISION) - function.apply(x)) / DERIVATIVE_PRECISION;
    }

    public static class Answer {
        private double xm;
        private double ym;

        public Answer(double xm, double ym) {
            this.xm = xm;
            this.ym = ym;
        }

        public double getXm() {
            return xm;
        }

        public double getYm() {
            return ym;
        }
    }
}

