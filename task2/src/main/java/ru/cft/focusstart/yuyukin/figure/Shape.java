package ru.cft.focusstart.yuyukin.figure;

public abstract class Shape {
    public abstract String getInformation();

    boolean isValidParam(Shape shape, Double... arg) {

        for (Double a : arg) {
            if (a <= 0) {
                return false;
            }
        }
        if (shape instanceof Triangle) {
            if (arg[0] > arg[1] + arg[2]) {
                return false;
            }
            if (arg[1] > arg[0] + arg[2]) {
                return false;
            }
            if (arg[2] > arg[0] + arg[1]) {
                return false;
            }
        }

        return true;
    }

}
