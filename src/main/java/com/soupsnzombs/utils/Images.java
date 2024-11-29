package com.soupsnzombs.utils;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class Images {
    // public static ArrayList<BufferedImage> player_running = new ArrayList<>();
    // public static ArrayList<BufferedImage> gunfire = new ArrayList<>();

    public static BufferedImage player_idle, circle, gun, bullet, tree, shop, gameMenu, background,
            playButton, creditsButton, scoresButton, arrowImage, shopBackground, grass;
    public static HashMap<String, BufferedImage> spriteImages = new HashMap<>();
    public static HashMap<String, BufferedImage> tileImages = new HashMap<>();

    public static void loadImages() {
        ArrayList<SpriteImage> sprites = readXML("s.xml");
        ArrayList<SpriteImage> tilesheet = readXML("t.xml");
        try {
            // main menu images
            background = ImageIO.read(Images.class.getResource("/bg.jpeg"));
            playButton = ImageIO.read(Images.class.getResource("/buttons/play.png"));
            scoresButton = ImageIO.read(Images.class.getResource("/buttons/scores.png"));
            creditsButton = ImageIO.read(Images.class.getResource("/buttons/credits.png"));
            arrowImage = ImageIO.read(Images.class.getResource("/arrow.png"));
            grass = ImageIO.read(Images.class.getResource("/grass.png"));
            // shop menu buttons
            playButton = scaleImage(playButton, 150, 50);
            scoresButton = scaleImage(scoresButton, 150, 50);
            creditsButton = scaleImage(creditsButton, 150, 50);
            arrowImage = scaleImage(arrowImage, 51, 130 / 2);
            shopBackground = ImageIO.read(Images.class.getResource("/SoupsNZombsShop.png"));

            // get the spritesheet, crop image, and set the spriteImages hashmap
            BufferedImage spriteSheet = ImageIO.read(Images.class.getResource("/spritesheet.png"));
            for (SpriteImage sprite : sprites) {
                BufferedImage croppedImage = spriteSheet.getSubimage(sprite.x, sprite.y, sprite.width, sprite.height);
                spriteImages.put(sprite.name, croppedImage);
                shopBackground = ImageIO.read(Images.class.getResource("/SoupsNZombsShop.png"));
            }

            // get the tilesheet, crop image, and set the tileImages hashmap
            BufferedImage tileSheet = ImageIO.read(Images.class.getResource("/tilesheet.png"));
            for (SpriteImage tile : tilesheet) {
                BufferedImage croppedImage = tileSheet.getSubimage(tile.x, tile.y, tile.width, tile.height);
                tileImages.put(tile.name, croppedImage);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static BufferedImage scaleImage(BufferedImage originalImage, int width, int height) {
        Image scaledImage = originalImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        BufferedImage bufferedScaledImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        bufferedScaledImage.getGraphics().drawImage(scaledImage, 0, 0, null);
        return bufferedScaledImage;
    }

    private static ArrayList<SpriteImage> readXML(String path) {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

        try {
            dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);

            // parse XML file
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(Images.class.getResourceAsStream("/" + path));
            doc.getDocumentElement().normalize();

            System.out.println("Root Element :" + doc.getDocumentElement().getNodeName());
            System.out.println("------");

            // get <staff>
            NodeList list = doc.getElementsByTagName("SubTexture");

            // arraylist to hold
            ArrayList<SpriteImage> spriteImages = new ArrayList<>();

            for (int temp = 0; temp < list.getLength(); temp++) {

                Node node = list.item(temp);

                // if (node.getNodeType() == Node.ELEMENT_NODE) {

                Element element = (Element) node;
                // <SubTexture name="hitman1_gun.png" x="164" y="88" width="49" height="43"
                // frameX="-0" frameY="-0" frameWidth="49" frameHeight="43"/>

                System.out.println("Name: " + element.getAttribute("name"));
                System.out.println("X: " + element.getAttribute("x"));
                System.out.println("Y: " + element.getAttribute("y"));
                System.out.println("Width: " + element.getAttribute("width"));
                System.out.println("Height: " + element.getAttribute("height"));

                spriteImages.add(new SpriteImage(element.getAttribute("name"),
                        Integer.parseInt(element.getAttribute("x")),
                        Integer.parseInt(element.getAttribute("y")), Integer.parseInt(element.getAttribute("width")),
                        Integer.parseInt(element.getAttribute("height")),
                        Integer.parseInt(element.getAttribute("frameX")),
                        Integer.parseInt(element.getAttribute("frameY")),
                        Integer.parseInt(element.getAttribute("frameWidth")),
                        Integer.parseInt(element.getAttribute("frameHeight"))));

            }

            return spriteImages;

        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }

        return null;

    }
}