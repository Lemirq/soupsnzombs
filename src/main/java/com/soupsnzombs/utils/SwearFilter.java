package com.soupsnzombs.utils;

import java.util.*;

public class SwearFilter {

    // List of prohibited words
    private static final List<String> SWEAR_WORDS = Arrays.asList(
        "arse", "arsehole", "ass", "asshat", "asshole", "bastard", "bitch", "bloody", "blowjob", "bollocks",
        "bugger", "bullshit", "buttfuck", "chickenshit", "clusterfuck", "cock", "cocksucker", "coonass", "cornhole",
        "crap", "cunt", "damn", "dick", "dickhead", "dildo", "dipshit", "douche", "douchebag", "faggot", "fag",
        "feck", "fellatio", "flogging the dolphin", "fuck", "fuckface", "fucked", "fucker", "fuckhead", "fuckwit",
        "goddamn", "hell", "horseshit", "jackass", "jerkoff", "jizz", "knobhead", "lameass", "minger", "motherfucker",
        "muff", "muffdiver", "nigga", "nigger", "nutjob", "nutsack", "paki", "pecker", "piss", "pissed", "pissflaps",
        "prick", "pussy", "queef", "retard", "scumbag", "shit", "shitass", "shitbag", "shitcanned", "shitfaced",
        "shithole", "shitter", "skank", "slut", "son of a bitch", "spastic", "spic", "tits", "titwank", "tosser",
        "twat", "wanker", "whore", "wank", "wankstain", "wankshaft"
    );

    /**
     * Checks if the input (word or text) is acceptable.
     *
     * @param input single word or sentence/phrase as a StringBuilder
     * @return true if acceptable, false if it contains one or more swear words
     */
    public static boolean isInputAccepted(StringBuilder input) {
        String normalizedText = normalizeWord(input.toString());
        String[] words = normalizedText.split("\\s+");
        int swearCount = 0;

        for (String word : words) {
            for (String swear : SWEAR_WORDS) {
                if (levenshteinDistance(word, swear) <= 2) {
                    swearCount++;
                    if (swearCount > 0) { // Reject if any swear word is found
                        return false;
                    }
                }
            }
        }
        return true; // Accepted if no swear words found
    }

    /**
     * Normalize input text by replacing common leetspeak substitutions.
     *
     * @param text input text
     * @return normalized text
     */
    private static String normalizeWord(String text) {
        return text.toLowerCase()
            .replaceAll("\\$", "s")
            .replaceAll("@", "a")
            .replaceAll("1", "l")
            .replaceAll("!", "i")
            .replaceAll("0", "o")
            .replaceAll("(.)\\1{2,}", "$1"); // Remove repeated characters
    }

    /**
     * Calculate Levenshtein distance.
     *
     * @param s1 first string
     * @param s2 second string
     * @return Levenshtein distance
     */
    private static int levenshteinDistance(String s1, String s2) {
        int[][] dp = new int[s1.length() + 1][s2.length() + 1];

        for (int i = 0; i <= s1.length(); i++) {
            for (int j = 0; j <= s2.length(); j++) {
                if (i == 0) {
                    dp[i][j] = j;
                } else if (j == 0) {
                    dp[i][j] = i;
                } else if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    dp[i][j] = 1 + Math.min(dp[i - 1][j - 1], Math.min(dp[i - 1][j], dp[i][j - 1]));
                }
            }
        }
        return dp[s1.length()][s2.length()];
    }
}
