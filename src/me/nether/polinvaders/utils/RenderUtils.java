package me.nether.polinvaders.utils;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class RenderUtils {

    private static Map<Character, Integer> colorMap = new HashMap<>();

    static {
        colorMap.put('4', 0xFFAA0000);
        colorMap.put('c', 0xFFFF5555);
        colorMap.put('6', 0xFFFFAA00);
        colorMap.put('e', 0xFFFFFF55);
        colorMap.put('2', 0xFF00AA00);
        colorMap.put('a', 0xFF55FF55);
        colorMap.put('b', 0xFF55FFFF);
        colorMap.put('3', 0xFF00AAAA);
        colorMap.put('1', 0xFF0000AA);
        colorMap.put('9', 0xFF5555FF);
        colorMap.put('d', 0xFFFF55FF);
        colorMap.put('5', 0xFFAA00AA);
        colorMap.put('f', 0xFFFFFFFF);
        colorMap.put('7', 0xFFAAAAAA);
        colorMap.put('8', 0xFF555555);
        colorMap.put('0', 0xFF000000);
    }

    public static void drawStringWithShadow(Graphics2D g, String string, int x, int y, int color) {
        String lastChars = "";
        g.setColor(ColorUtils.getColor(color).darker().darker().darker().darker());
        for (int i = 0; i < string.toCharArray().length; i++) {
            char c = string.charAt(i);
            if (string.substring(i).startsWith("\247")) {
                char cColor = string.substring(i).charAt(1);
                g.setColor(ColorUtils.getColor(colorMap.get(cColor)).darker().darker().darker().darker());
                i++;
                continue;
            }

            g.drawString(c + "", x + g.getFontMetrics().stringWidth(lastChars) + 1.3f, y + 1.3f);
            lastChars += c;
        }

        drawString(g, string, x, y, color);
    }

    public static void drawString(Graphics2D g, String string, int x, int y, int color) {
        String lastChars = "";
        g.setColor(ColorUtils.getColor(color));
        for (int i = 0; i < string.toCharArray().length; i++) {
            char c = string.charAt(i);
            if (string.substring(i).startsWith("\247")) {
                char cColor = string.substring(i).charAt(1);
                g.setColor(ColorUtils.getColor(colorMap.get(cColor)));
                i++;
                continue;
            }

            g.drawString(c + "", x + g.getFontMetrics().stringWidth(lastChars), y);
            lastChars += c;
        }
    }

    public static int getStringWidth(Graphics2D g, String string) {
        int width = 0;
        for (int i = 0; i < string.toCharArray().length; i++) {
            char c = string.charAt(i);
            if (string.substring(i).startsWith("\247")) {
                i++;
                continue;
            }
            width += g.getFontMetrics().stringWidth(c + "");
        }
        return width;
    }

    public static int getStringWidth(Graphics2D g, Font font, String string) {
        g.setFont(font);
        int width = 0;
        for (int i = 0; i < string.toCharArray().length; i++) {
            char c = string.charAt(i);
            if (string.substring(i).startsWith("\247")) {
                i++;
                continue;
            }
            width += g.getFontMetrics().stringWidth(c + "");
        }
        return width;
    }

}
