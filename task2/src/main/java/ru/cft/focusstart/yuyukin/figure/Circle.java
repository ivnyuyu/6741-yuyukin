package ru.cft.focusstart.yuyukin.figure;

public class Circle extends Shape {
    private final static String NAME = "Круг";
    private double radius;

    Circle(double radius) throws Exception {
        if (!super.isValidParam(this, radius)) {
            throw new Exception("Не валидные данные для " + NAME);
        }
        this.radius = radius;
    }


    private double getSquare() {
        return Math.PI * radius * radius;
    }

    private double getPerimeter() {
        return 2 * Math.PI * radius;
    }

    private double getDiameter() {
        return 2 * radius;
    }

    @Override
    public String getInformation() {
        return String.format("Тип фигуры: %s" +
                        "\nПлощадь: %.2f" +
                        "\nПериметр: %.2f" +
                        "\nРадиус: %.2f" +
                        "\nДиаметр: %.2f"
                , NAME, getSquare(), getPerimeter(), radius, getDiameter());

    }


}
