package controllers;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class ArthurTest {
    public static void main(String[] args) {
        String URI = new File("src/main/resources/sound/arthur.mp3").toURI().toString();
        Media media = new Media(URI);
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.play();
    }
}