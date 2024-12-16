package com.soupsnzombs.utils;

import com.soupsnzombs.GamePanel;
import com.soupsnzombs.UI.MainMenu.NameSelect;
import com.soupsnzombs.entities.Player;

import java.util.*;

import javax.swing.JOptionPane;

import java.io.*;

public class Leaderboard {
    static File leaderboard = new File("G:\\My Drive\\ICS4U\\Culminating\\Prototype\\soupsnzombs\\src\\main\\resources\\leaderboard.txt");
    static FileReader fr;
    static BufferedReader br;
    static FileWriter fw;
    static BufferedWriter bw;
    static String line;
    static Scanner sc = new Scanner(System.in);
    HashMap<String, Integer> scores = new HashMap<String, Integer>();

    public static void setupLeaderboard() {
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

    // public static void readScores() {
    //     try {
    //         // add scores and names to hashmp
    //         fr = new FileReader(leaderboard);
    //         br = new BufferedReader(fr);
    //         while ((line = br.readLine()) != null) {
    //             String[] parts = line.split(" ");
    //             String name = parts[0];
    //             int score = Integer.parseInt(parts[1]);
    //             System.out.println(name + " " + score);
    //         }
    //         br.close();
    //         fr.close();

    //     } catch (Exception e) {
    //         JOptionPane.showMessageDialog(null, "Error reading file.");
    //     }
    // }

    public static HashMap<String, Integer> readScores() {
        HashMap<String, Integer> leaderboardMap = new HashMap<>();
        
        try {
            FileReader fr = new FileReader(leaderboard);
            BufferedReader br = new BufferedReader(fr);
    
            String line;
    
            while ((line = br.readLine()) != null) { 
                String[] parts = line.split(" score:");
                if (parts.length == 2) {
                    String name = parts[0].replace("name:", "").trim();
                    String scoreStr = parts[1].trim();
                    
                    try {
                        int score = Integer.parseInt(scoreStr);
                        leaderboardMap.put(name, score);
                    } catch (NumberFormatException e) {
                        System.err.println("Invalid score format: " + scoreStr);
                    }
                } else {
                    System.err.println("Invalid line format: " + line);
                }
            }
    
            br.close();
            fr.close();
        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
        }
    
        List<Map.Entry<String, Integer>> leaderboardList = new ArrayList<>(leaderboardMap.entrySet());
        
        leaderboardList.sort((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()));
        
        HashMap<String, Integer> sortedLeaderboardMap = new LinkedHashMap<>();
        
        int count = 0;
        for (Map.Entry<String, Integer> entry : leaderboardList) {
            sortedLeaderboardMap.put(entry.getKey(), entry.getValue());
            count++;
            if (count == 10)break;
        }
    
        return sortedLeaderboardMap;
    }
    
    
    

    public static void writeScores() {
        try {
            if (!leaderboard.exists()) {
                leaderboard.createNewFile();
            }
            fw = new FileWriter(leaderboard, true);
            bw = new BufferedWriter(fw);
            bw.write("name:" + NameSelect.name + " " + "score:" + Player.score + " " + "time survived:" + String.format("%.2f", GamePanel.elapsedTime));
            bw.newLine();
            bw.close();
            fw.close();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error writing to file.");
            System.err.println("IOException: " + e.getMessage());
        }
    }
}