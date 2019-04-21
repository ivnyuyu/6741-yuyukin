package ru.cft.focusstart.yuyukin;


import ru.cft.focusstart.yuyukin.figure.FileOutput;
import ru.cft.focusstart.yuyukin.figure.Reader;
import ru.cft.focusstart.yuyukin.figure.Shape;

public class App {
    private static String inputFile;
    private static String outputFile;

    public static void main(String[] args) {

        try {
            parseArgs(args);
            Shape shape = new Reader().getShapeFromFile(inputFile);
            if (outputFile != null) {
                new FileOutput().write(shape, outputFile);
            } else {
                System.out.println(shape.getInformation());
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    private static void parseArgs(String[] args) throws Exception {
        if (args.length == 0) {
            throw new Exception("Отсутсвуют аргументы в командной строке");
        }
        inputFile = args[0];
        if (args.length > 1) {
            outputFile = args[1];
        }
    }
}
