package ru.cft.focusstart.yuyukin;

import ru.cft.focusstart.yuyukin.figure.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class Reader {
    final private static int SIZE_CIRCLE_ARG = 1;
    final private static int SIZE_RECTANGLE_ARG = 2;
    final private static int SIZE_TRIANGLE_ARG = 3;

    Shape getShapeFromFile(String file) throws Exception {
        String nameShape;
        String paramShape;
        List<Double> params = new ArrayList<>();
        try (BufferedReader dataFromFile = new BufferedReader(new FileReader(file))) {
            nameShape = dataFromFile.readLine();
            paramShape = dataFromFile.readLine();
            if (paramShape != null) {
                String[] tempParam = paramShape.split(" ");
                for (String param : tempParam) {
                    params.add(Double.parseDouble(param));
                }
            }
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("Файл не был найден: " + file);
        } catch (IOException e) {
            throw new IOException("Не удалось считать с файла:" + file);
        } catch (NumberFormatException e) {
            throw new Exception("Файл содержит переменную отличную от числа");
        }
        return getShapeFromData(nameShape, params);
    }

    private Shape getShapeFromData(String nameShape, List<Double> params) throws Exception {
        int sizeParams = params.size();
        if (nameShape.equals(ShapeType.CIRCLE.name()) && sizeParams == SIZE_CIRCLE_ARG) {
            return new Circle(params.get(0));
        }
        if (nameShape.equals(ShapeType.RECTANGLE.name()) && sizeParams == SIZE_RECTANGLE_ARG) {
            return new Rectangle(params.get(0), params.get(1));
        }
        if (nameShape.equals(ShapeType.TRIANGLE.name()) && sizeParams == SIZE_TRIANGLE_ARG) {
            return new Triangle(params.get(0), params.get(1), params.get(2));
        }
        throw new Exception("Невозможно создать объект типа Shape с названием " + nameShape + " и набором аргументов" + params);
    }
}
