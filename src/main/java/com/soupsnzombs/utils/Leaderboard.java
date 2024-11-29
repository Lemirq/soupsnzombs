package com.soupsnzombs.utils;

import javax.swing.*;
import java.util.*;
import java.io.*;

public class Leaderboard {
    static File leaderboard = new File("leaderboard.txt");
    static FileReader fr;
    static BufferedReader br;
    static FileWriter fw;
    static BufferedWriter bw;
    static String line;
    static Scanner sc = new Scanner(System.in);

    public static void writeScores() {
        if (leaderboard.exists()) {
            System.out.println("Leaderboard already exists.");
        } else {
            try {
                leaderboard.createNewFile();
                System.out.println("New file created.");
                fr = new FileReader(leaderboard);
                br = new BufferedReader(fr);
                while ((line = br.readLine()) != null) {
                    System.out.println(line);
                }
                br.close();
                fr.close();
            } catch (FileNotFoundException e) {
                System.out.println("File not found.");
                System.err.println("FileNotFoundException: " + e.getMessage());
            } catch (IOException e) {
                System.out.println("Leaderboard.txt file could not be created.");
                System.err.println("IOException: " + e.getMessage());
            }

        }
    }

    public static void readScores() {
        try {
                System.out.println("Enter your name:");
                String name = sc.next();
                

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public static void main(String[] args) {
        writeScores();
        readScores();


    }
}