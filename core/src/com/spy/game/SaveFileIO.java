package com.spy.game;
import java.io.*;
import java.math.BigDecimal;
import java.util.Scanner;

/**
 * Created by qowie on 12/14/2016.
 */
public class SaveFileIO {
    private String saveName = "score.sav";
    FileReader in;
    FileWriter out;
    int high;
    public SaveFileIO() throws IOException {
       // in = new FileReader(saveName);
        //out = new FileWriter(saveName);
        //saveScore(10);

    }
    public int loadScore() throws FileNotFoundException {
        File logFile = new File(saveName);
        Scanner scanner = new Scanner(logFile);
        BigDecimal out = scanner.nextBigDecimal();
        return out.intValue();/*
        int [] tall = new int [100];
        int i = 0;
        while(scanner.hasNextInt())
        {
            tall[i++] = scanner.nextInt();
        }
        */
    }

    public void saveScore(int score) throws IOException {
        high = loadScore();
        BufferedWriter writer = null;
        try {
            //create a temporary file
           // String timeLog = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
            File logFile = new File(saveName);
            System.out.println(logFile.getCanonicalPath());

            writer = new BufferedWriter(new FileWriter(logFile));


            if(high>score){
                System.out.println("\n------------\n!YOU GET HIGHSCORE!");
                System.out.println("Your score : " + score);
                writer.write(Integer.toString(score));
            }else{
                System.out.println("");
                System.out.println("Your score : " + score);
                writer.write(Integer.toString(score));
            }

            // This will output the full path where the file will be written to...

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                // Close the writer regardless of what happens...
                writer.close();
            } catch (Exception e) {
            }
        }

    }
}
