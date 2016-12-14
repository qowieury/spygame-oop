package com.spy.game;
import java.io.*;

/**
 * Created by qowie on 12/14/2016.
 */
public class SaveFileIO {
    private String saveName = "score.sav";
    FileReader in;
    FileWriter out;

    public SaveFileIO() throws IOException {
        in = new FileReader(saveName);
        out = new FileWriter(saveName);
        saveScore(10);

    }
    public void saveScore(int score) throws IOException {
        out.write(score);

    }
}
