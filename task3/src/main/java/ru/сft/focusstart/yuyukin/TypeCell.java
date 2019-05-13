package ru.сft.focusstart.yuyukin;

public enum TypeCell {
    Closed("/icons/closed.png"),
    Flaged("/icons/flag.png"),
    Mine("/icons/mine.png"),
    Nomine("/icons/no_mine.png"),
    Zero("/icons/zero.png"),
    One("/icons/one.png"),
    Two("/icons/two.png"),
    Three("/icons/three.png"),
    Four("/icons/four.png"),
    Five("/icons/five.png"),
    Six("/icons/six.png"),
    Seven("/icons/seven.png"),
    Eight("/icons/eight.png");

    private String urlImage;

    TypeCell(String urlImage) {
        this.urlImage = urlImage;
    }
    public String getUrlImage() {return urlImage;}
}
