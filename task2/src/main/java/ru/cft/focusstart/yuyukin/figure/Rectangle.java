package ru.cft.focusstart.yuyukin.figure;

public class Rectangle extends Shape {
    private final static String name = "Прямоугольник";
    private double length;
    private double width;

    Rectangle(double length, double width) throws Exception {
        if (!super.isValidParam(this, length, width)) {
            throw new Exception("Не валидные параметры для " + name);
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

    public String getInformation() {
        return String.format("Тип фигуры: %s" +
                        "\nПлощадь: %.2f" +
                        "\nПериметр: %.2f" +
                        "\nДиагональ: %.2f" +
                        "\nШирина: %.2f " +
                        "\nВысота: %.2f ",
                name, getSquare(), getPerimeter(), getDiagonal(), width, length);
    }

}
