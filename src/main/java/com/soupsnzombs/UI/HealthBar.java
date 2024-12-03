package com.soupsnzombs.UI;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;

import com.soupsnzombs.GamePanel;
import com.soupsnzombs.utils.Theme;

public class HealthBar {
    // Constants for the health bar dimensions and position
    private static final int BAR_WIDTH = 200;
    private static final int BAR_HEIGHT = 30;
    private static final int roundRectArc = 10;

    private int healthValue;

    public HealthBar(int value) {
        this.healthValue = value;

    }

    // Setter to update health
    public void setHealthValue(int value) {
        this.healthValue = value;
    }

    public void draw(Graphics2D g2d) {
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Determine the health bar color based on the health value
        Color fillColor;
        if (healthValue < 50) {
            fillColor = Color.RED; // Less than 50% health
        } else if (healthValue < 75) {
            fillColor = Color.YELLOW; // Less than 75% health
        } else {
            fillColor = Theme.HEALTH_BAR_FG; // 75% or more health
        }
        int X_POSITION = 100;
        int Y_POSITION = (GamePanel.screenHeight) - 100;

        // Save the original transform
        AffineTransform originalTransform = g2d.getTransform();

        // Apply translation and skew transformation
        AffineTransform skewTransform = new AffineTransform();
        skewTransform.translate(X_POSITION, Y_POSITION);
        skewTransform.shear(-0.5, 0); // Skew horizontally by 0.5
        g2d.setTransform(skewTransform);

        // Draw the underlying health bar gray fill
        g2d.setColor(Theme.HEALTH_BAR_BG);
        g2d.fillRoundRect(0, 0, BAR_WIDTH, BAR_HEIGHT, roundRectArc, roundRectArc);

        // Fill the health bar based on the current health percentage
        int fillWidth = (int) ((healthValue / 100.0) * BAR_WIDTH);
        g2d.setColor(fillColor);
        g2d.fillRoundRect(0, 0, fillWidth, BAR_HEIGHT, roundRectArc, roundRectArc);

        // Restore the original transform
        g2d.setTransform(originalTransform);

        // Set a larger font size for the health value text
        g2d.setFont(new Font("DejaVuSans 12", Font.BOLD, 24));

        // Draw the health value text
        g2d.setColor(Color.WHITE);
        g2d.drawString(String.valueOf(healthValue), X_POSITION + BAR_WIDTH + 18, Y_POSITION + BAR_HEIGHT - 10);

        // clear the font
        g2d.setFont(new Font("DejaVuSans 12", Font.PLAIN, 12));
    }
}