package ru.сft.focusstart.yuyukin.util;

import ru.сft.focusstart.yuyukin.model.TypeCell;

public class Convert {
    public static TypeCell nameCell(int number){
        switch (number){
            case 0: return TypeCell.Zero;
            case 1: return TypeCell.One;
            case 2: return TypeCell.Two;
            case 3: return TypeCell.Three;
            case 4: return TypeCell.Four;
            case 5: return TypeCell.Five;
            case 6: return TypeCell.Six;
            case 7: return TypeCell.Seven;
            case 8: return TypeCell.Eight;
            case -1: return TypeCell.Mine;
        }
        return TypeCell.Closed;
    }
}
