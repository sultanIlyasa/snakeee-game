package Snake.Algorithm;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Score {
    private int score;

    public Score() {
        this.score = 0;
    }

    public int getScore() {
        return score;
    }

    public void increaseScore() {
        this.score += 40;
    }

    public void resetScore() {
        this.score = 0;
    }

    public void saveScore() throws IOException {
        String newScore = String.valueOf(this.score);

        File scoreFile = new File("src/main/resources/highscore.dat");
        if (!scoreFile.exists()) {
            try {
                scoreFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        FileWriter writeFile = null;
        BufferedWriter writer = null;

        try {
            writeFile = new FileWriter(scoreFile, true);
            writer = new BufferedWriter(writeFile);
            writer.write(newScore);
        } catch (Exception e) {
            return;
        } finally {
            try {
                if (writer != null)
                    writer.close();
            } catch (Exception e) {
                return;
            }
        }
    }

    public String readScore() throws FileNotFoundException {
        FileReader readFile = null;
        BufferedReader reader = null;
        try {
            // ReadFile highscore.dat
            readFile = new FileReader("src/main/resources/highscore.dat");
            reader = new BufferedReader(readFile);

            String line = reader.readLine();
            String allLines = line;

            while (line != null) {
                // Baca per baris
                line = reader.readLine();
                // Untuk error handling
                if (line == null)
                    break;
                // Menggabungkan baris
                allLines = allLines.concat("\n" + line);
            }

            // return String yang persis seperti isi dari highscore.dat
            return allLines;
        }
        // Jika highscore.dat nya tidak ada
        catch (Exception e) {
            return "0\n0\n0\n0\n0\n0\n0\n0\n0\n0";
        } finally {
            try {
                // Menutup readernya
                if (reader != null)
                    reader.close();
            } // Jika terjadi exception
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void sortHighScore() {
        FileReader readFile = null;
        BufferedReader reader = null;
        FileWriter writeFile = null;
        BufferedWriter writer = null;
        List<Integer> list = new ArrayList<Integer>();
        try {
            readFile = new FileReader("src/main/resources/highscore.dat");
            reader = new BufferedReader(readFile);

            String line = reader.readLine();

            // Pindahkan isi dari highscore.dat ke sebuah array List
            while (line != null) {
                list.add(Integer.parseInt(line));
                line = reader.readLine();
            }

            // Sort array listnya
            Collections.sort(list);

            // Me-reverse agar jadi descending
            Collections.reverse(list);

            // Tulis ke highscore.dat, score yang sudah diurutkan
            writeFile = new FileWriter("src/main/resources/highscore.dat");
            writer = new BufferedWriter(writeFile);

            int size = list.size();

            // Nantinya akan hanya 10 skor teratas yang ditulis kembali
            for (int i = 0; i < 10; i++) {
                // Ini untuk mengisi nilai lainnya jadi 0
                if (i > size - 1) {
                    String def = "0";
                    writer.write(def);
                } else { // Mengambil satu-satu dari list
                    String str = String.valueOf(list.get(i));
                    writer.write(str);
                }
                if (i < 9) {// This prevent creating a blank like at the end of the file**
                    writer.newLine();
                }
            }
        } catch (Exception e) {
            return;
        } finally {
            try {
                // Menutup readernya
                if (reader != null)
                    reader.close();
                // Menutup writer
                if (writer != null)
                    writer.close();
            } // Jika terjadi exception
            catch (IOException e) {
                return;
            }
        }

    }
}
