package me.nether.polinvaders.utils;

import java.awt.*;

public class ColorUtils {

    public static float[] getHSVFromRGB(int r, int g, int b) {
        float h, s, v;

        float rr, gg, bb;
        rr = r / 255.0f;
        gg = g / 255.0f;
        bb = b / 255.0f;

        float min, max;
        min = Math.min(rr, Math.min(gg, bb));
        max = Math.max(rr, Math.max(gg, bb));

        v = max;

        float diff = max - min;
        s = max == 0 ? 0 : diff / max;

        if (max == min)
            h = 0; //Shade of gray
        else {
            if (max == rr)
                h = (gg - bb) / diff + (gg < bb ? 6 : 0);
            else if (max == gg)
                h = (bb - rr) / diff + 2;
            else
                h = (rr - gg) / diff + 4;
            h /= 6;
        }

        return new float[]{h, s, v};
    }

    public static float[] getHSVFromRGB(float r, float g, float b) {
        float h, s, v;

        float min, max;
        min = Math.min(r, Math.min(g, b));
        max = Math.max(r, Math.max(g, b));

        v = max;

        float diff = max - min;
        s = max == 0 ? 0 : diff / max;

        if (max == min)
            h = 0; //Shade of gray
        else {
            if (max == r)
                h = (g - b) / diff + (g < b ? 6 : 0);
            else if (max == g)
                h = (b - r) / diff + 2;
            else
                h = (r - g) / diff + 4;
            h /= 6;
        }

        return new float[]{h, s, v};
    }

    public static float[] getRGBFromHSV(float h, float s, float v) {
        float r, g, b;
        float var_h, var_i, var_1, var_2, var_3, var_r, var_g, var_b;
        if (s == 0) {
            r = v;
            g = v;
            b = v;
        } else {
            var_h = h * 6;
            if (var_h == 6) var_h = 0;
            var_i = (int) var_h;
            var_1 = v * (1 - s);
            var_2 = v * (1 - s * (var_h - var_i));
            var_3 = v * (1 - s * (1 - (var_h - var_i)));

            if (var_i == 0) {
                var_r = v;
                var_g = var_3;
                var_b = var_1;
            } else if (var_i == 1) {
                var_r = var_2;
                var_g = v;
                var_b = var_1;
            } else if (var_i == 2) {
                var_r = var_1;
                var_g = v;
                var_b = var_3;
            } else if (var_i == 3) {
                var_r = var_1;
                var_g = var_2;
                var_b = v;
            } else if (var_i == 4) {
                var_r = var_3;
                var_g = var_1;
                var_b = v;
            } else {
                var_r = v;
                var_g = var_1;
                var_b = var_2;
            }

            r = var_r;
            g = var_g;
            b = var_b;
        }
        return new float[]{r, g, b};
    }

    /**
     * Return a float array of RGB converted module a hex integer.
     *
     * @param par1Hex Hex colour code
     * @return RGB float array
     */
    public static float[] getARGB(final int par1Hex) {
        final float a = (par1Hex >> 24 & 255) / 255f;
        final float r = (par1Hex >> 16 & 255) / 255f;
        final float g = (par1Hex >> 8 & 255) / 255f;
        final float b = (par1Hex & 255) / 255f;

        return new float[]{a, r, g, b};
    }

    /**
     * Return a float array of RGBA converted module a hex integer.
     *
     * @param hex Hex colour code
     * @return RGB float array
     */
    public static float[] getRGBA(int hex) {
        float red = (hex >> 16 & 0xFF) / 255.0F;
        float green = (hex >> 8 & 0xFF) / 255.0F;
        float blue = (hex & 0xFF) / 255.0F;
        float alpha = (hex >> 24 & 0xFF) / 255.0F;
        return new float[]{red, green, blue, alpha};
    }

    /**
     * Returns a java.awt.Color instance from an int color value
     *
     * @param hexColor
     * @return new Color()
     */
    public static Color getColor(int hexColor) {
        float[] c = getRGBA(hexColor);
        return new Color(c[0], c[1], c[2], c[3]);
    }

    /**
     * Returns a color from RGBA components
     * [0 - 255] range
     *
     * @param red
     * @param green
     * @param blue
     * @param alpha
     * @return int color
     */
    public static int getColor(int red, int green, int blue, int alpha) {
        int color = 0;
        alpha = alpha == 0 ? 255 : alpha;
        color |= alpha << 24;
        color |= red << 16;
        color |= green << 8;
        color |= blue;
        return color;
    }

    /**
     * Same but with float values
     * [0 - 1] range
     *
     * @param red
     * @param green
     * @param blue
     * @param alpha
     * @return int color
     */
    public static int getColor(float red, float green, float blue, float alpha) {
        return getColor((int) (red * 255), (int) (green * 255), (int) (blue * 255), (int) (alpha * 255));
    }

    /**
     * Same but with float values from an array
     * [0 - 1] range
     *
     * @param rgba array
     * @return int color
     */
    public static int getColor(float rgba[]) {
        return getColor((int) (rgba[0] * 255), (int) (rgba[1] * 255), (int) (rgba[2] * 255), (int) (rgba[3] * 255));
    }

    public static int getColor(float rgb[], float alpha) {
        return getColor((int) (rgb[0] * 255), (int) (rgb[1] * 255), (int) (rgb[2] * 255), (int) (alpha * 255));
    }

    /**
     * TODO: TEST
     * @param value
     * @param start
     * @param end
     * @return
     */
    public static int getColorInRange(int value, int start, int end) {
        int c = (int) MathUtils.map(value, start, end, 0, 255);

        return getColor(c, c, c, 255);
    }

    /**
     * TODO: TEST
     * @param value
     * @param start
     * @param end
     * @return
     */
    public static float getColorInRange(float value, float start, float end) {
        float c = (float) MathUtils.map(value, start, end, 0, 1);

        return getColor(c, c, c, 1);
    }
}
