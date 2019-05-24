package ru.сft.focusstart.yuyukin.model;

public enum GameStatus {
    Running("/icons/running.png"),
    Won("/icons/windeb.png"),
    Lost("/icons/lost.png");

    private String urlImage;

    GameStatus(String urlImage) {
        this.urlImage = urlImage;
    }

    public String getUrlImage() {
        return urlImage;
    }
}
