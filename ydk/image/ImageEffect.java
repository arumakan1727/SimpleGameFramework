package ydk.image;

import static ydk.image.RGBUtil.*;

import java.awt.image.BufferedImage;

public class ImageEffect
{
    private ImageEffect() {}

    public static void reverseColor(BufferedImage img)
    {
        final int H = img.getHeight();
        final int W = img.getWidth();

        for (int y = 0; y < H; y++) {
            for (int x = 0; x < W; x++) {
                int c = img.getRGB(x, y);
                int rgb = makeRGB(255 - getA(c),
                                255 - getG(c),
                                255 - getB(c));
                img.setRGB(x, y, rgb);
            }
        }
    }

    public static void grayColor(BufferedImage img)
    {
        final int H = img.getHeight();
        final int W = img.getWidth();

        for (int y = 0; y < H; y++) {
            for (int x = 0; x < W; x++) {

                final int c = img.getRGB(x, y);
                int p;

                final int R = getR(c);
                final int G = getR(c);
                final int B = getB(c);

                if (R == G && G == B) {
                    p = R;
                } else {
                    p = (int) ( 0.2989*R + 0.5866*G + 0.1145*B );
                }
                img.setRGB(x, y, makeRGB(p,p,p));
            }
        }
    }

    public static void binarization(BufferedImage img, int shreshold, boolean invert)
    {
        final int H = img.getHeight();
        final int W = img.getWidth();

        for (int y = 0; y < H; y++) {
            for (int x = 0; x < W; x++) {
                final int c = img.getRGB(x, y);
                int p = (getA(c) + getR(c) + getB(c)) / 3;

                if (p < shreshold || invert) //しきい値 shreshold 未満 or 反転 なら黒
                    img.setRGB(x, y, 0);
                else                //白
                    img.setRGB(x, y, 0xffff_ffff);
            }
        }
    }

    public static void blur(BufferedImage img, int scale) throws IllegalArgumentException
    {
        if (scale < 0) throw new IllegalArgumentException();
        final int H = img.getHeight();
        final int W = img.getWidth();
        int newColor;

        for (int y = 0; y < H; y++) {
            for (int x = 0; x < W; x++) {
                if (x-scale < 0 || y-scale < 0 || x+scale >= W || y+scale >= H) {
                    newColor = img.getRGB(x, y);
                } else {
                    int sumR = 0, sumG = 0, sumB = 0;
                    final int S = (2*scale + 1) * (2*scale + 1);

                    for (int py = y-scale; py <= y+scale; ++py) {
                        for (int px = x-scale; px <= x+scale; ++px) {
                            final int color = img.getRGB(px, py);
                            sumR += getR(color);
                            sumG += getG(color);
                            sumB += getB(color);
                        }
                    }
                    sumR /= S;
                    sumG /= S;
                    sumB /= S;
                    newColor = makeRGB(sumR, sumG, sumB);
                }
                img.setRGB(x, y, newColor);
            }
        }
    }

}
