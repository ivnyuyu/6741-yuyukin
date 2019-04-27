package ru.cft.focusstart.yuyukin;

import ru.cft.focusstart.yuyukin.figure.Shape;

import java.io.FileWriter;
import java.io.IOException;

class FileOutput {
    void write(Shape shape, String file) throws Exception {
        try (FileWriter fileWriter = new FileWriter(file)) {
            fileWriter.write(shape.getInformation());
        } catch (IOException e) {
            throw new Exception("Не удалось записать в файл: " + file);
        }
    }
}
