package com.zeromods.dupecontrols.storage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class StorageUtils {
    public static void createFile(String path) {
        try {
            File myObj = new File(path);
            myObj.createNewFile();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public static void writeToFile(String path, String... lines) {
        try {
            FileWriter myWriter = new FileWriter(path);
            for (String line : lines)
                myWriter.write(line);
            myWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public static void resetFile(String path) {
        new File(path).delete();
        createFile(path);
    }

    public static List<String> readFile(String path) {
        List<String> returnable = new ArrayList<>();
        try {
            File myObj = new File(path);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                returnable.add(myReader.nextLine());
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        return returnable;
    }
}
