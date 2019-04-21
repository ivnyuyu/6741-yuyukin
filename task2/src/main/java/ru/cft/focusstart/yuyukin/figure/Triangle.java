package ru.cft.focusstart.yuyukin.figure;


public class Triangle extends Shape {
    private final static String name = "Треугольник";
    private double firstSide;
    private double secondSide;
    private double thirdSide;

    Triangle(double firstSide, double secondSide, double thirdSide) throws Exception {
        if (!super.isValidParam(this, firstSide, secondSide, thirdSide)) {
            throw new Exception("Не валидные параметры для " + name);
        }
        isValidParam(this, firstSide, secondSide, thirdSide);
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


    public String getInformation() {
        return String.format("Тип фигуры: %s" +
                        "\nПлощадь: %.2f" +
                        "\nПериметр: %.2f" +
                        "\nСторона размером %.2f  лежит напротив угла в  %.2f градусов" +
                        "\nСторона размером %.2f  лежит напротив угла в  %.2f градусов" +
                        "\nСторона размером %.2f  лежит напротив угла в  %.2f градусов"

                , name,
                getSquare(),
                getPerimeter(),
                firstSide, getAngle(firstSide, secondSide, thirdSide),
                secondSide, getAngle(secondSide, firstSide, thirdSide),
                thirdSide, getAngle(thirdSide, firstSide, secondSide));
    }

}
