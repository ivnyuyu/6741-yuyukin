package ru.cft.focusstart.yuyukin.figure;

public class Triangle extends Shape {
    private double firstSide;
    private double secondSide;
    private double thirdSide;

    public Triangle(double firstSide, double secondSide, double thirdSide) {
        if (!isValidParam()) {
            throw new IllegalArgumentException("Не валидные параметры для " + getName());
        }
        this.firstSide = firstSide;
        this.secondSide = secondSide;
        this.thirdSide = thirdSide;
    }

    private double getPerimeter() {
        return firstSide + secondSide + thirdSide;
    }

    private double getSquare() {
        double halfPerimeter = getPerimeter() / 2;
        return Math.sqrt(halfPerimeter * (halfPerimeter - firstSide) * (halfPerimeter - secondSide) * (halfPerimeter - thirdSide));
    }

    private double getAngle(double a, double b, double c) {
        return Math.toDegrees(Math.acos((b * b + c * c - a * a) / (2 * b * c)));
    }

    @Override
    boolean isValidParam() {
        if (firstSide <= 0 || secondSide <= 0 || thirdSide <= 0) {
            return false;
        }
        if (firstSide > secondSide + thirdSide) {
            return false;
        }
        if (secondSide > firstSide + thirdSide) {
            return false;
        }
        if (thirdSide > secondSide + firstSide) {
            return false;
        }
        return true;
    }

    @Override
    public String getName() {
        return "Треугольник";
    }

    @Override
    public String getInformation() {
        return String.format("Тип фигуры: %s" +
                        "\nПлощадь: %.2f" +
                        "\nПериметр: %.2f" +
                        "\nСторона размером %.2f  лежит напротив угла в  %.2f градусов" +
                        "\nСторона размером %.2f  лежит напротив угла в  %.2f градусов" +
                        "\nСторона размером %.2f  лежит напротив угла в  %.2f градусов",
                getName(),
                getSquare(),
                getPerimeter(),
                firstSide, getAngle(firstSide, secondSide, thirdSide),
                secondSide, getAngle(secondSide, firstSide, thirdSide),
                thirdSide, getAngle(thirdSide, firstSide, secondSide));
    }
}
