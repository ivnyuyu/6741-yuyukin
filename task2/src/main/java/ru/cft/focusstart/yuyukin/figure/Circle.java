package ru.cft.focusstart.yuyukin.figure;

public class Circle extends Shape {
    private double radius;

    public Circle(double radius) {
        if (!isValidParam()) {
            throw new IllegalArgumentException("Не валидные параметры для " + getName());
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
    boolean isValidParam() {
        return radius > 0;
    }

    @Override
    public String getName() {
        return "Круг";
    }

    @Override
    public String getInformation() {
        return String.format("Тип фигуры: %s" +
                        "\nПлощадь: %.2f" +
                        "\nПериметр: %.2f" +
                        "\nРадиус: %.2f" +
                        "\nДиаметр: %.2f",
                getName(), getSquare(), getPerimeter(), radius, getDiameter());
    }
}
