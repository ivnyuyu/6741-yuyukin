package ru.cft.focusstart.yuyukin.figure;

public class Rectangle extends Shape {
    private double length;
    private double width;

    public Rectangle(double length, double width) {
        if (!isValidParam()) {
            throw new IllegalArgumentException("Не валидные параметры для " + getName());
        }
        this.length = length;
        this.width = width;
    }

    private double getSquare() {
        return length * width;
    }

    private double getPerimeter() {
        return 2 * length + 2 * width;
    }

    private double getDiagonal() {
        return Math.sqrt((length * length + width * width));
    }

    @Override
    boolean isValidParam() {
        return length > 0 && width > 0;
    }

    @Override
    public String getName() {
        return "Прямоугольник";
    }

    @Override
    public String getInformation() {
        return String.format("Тип фигуры: %s" +
                        "\nПлощадь: %.2f" +
                        "\nПериметр: %.2f" +
                        "\nДиагональ: %.2f" +
                        "\nШирина: %.2f " +
                        "\nВысота: %.2f ",
                getName(), getSquare(), getPerimeter(), getDiagonal(), width, length);
    }
}
