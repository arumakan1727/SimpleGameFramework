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
        final int[] pixels = img.getRGB(0, 0,W, H, null, 0, W);

        for (int y = 0; y < H; y++) {
            for (int x = 0; x < W; x++) {
                final int c = pixels[W*y + x];
                pixels[W*y + x] =  makeRGB(
                        255 - getA(c),
                        255 - getG(c),
                        255 - getB(c));
            }
        }
        img.setRGB(0, 0, W, H, pixels, 0, W);
    }

    public static void grayColor(BufferedImage img)
    {
        final int H = img.getHeight();
        final int W = img.getWidth();
        final int[] pixels = img.getRGB(0, 0,W, H, null, 0, W);

        for (int y = 0; y < H; y++) {
            for (int x = 0; x < W; x++) {
                final int c = pixels[W*y + x];
                int p;

                final int R = getR(c);
                final int G = getR(c);
                final int B = getB(c);

                if (R == G && G == B) {
                    p = R;
                } else {
                    p = (int) ( 0.2989*R + 0.5866*G + 0.1145*B );
                }
                pixels[W*y + x] = makeRGB(p,p,p);
            }
        }
        img.setRGB(0, 0, W, H, pixels, 0, W);
    }

    public static void binarization(BufferedImage img, int shreshold, boolean invert)
    {
        final int H = img.getHeight();
        final int W = img.getWidth();
        final int[] pixels = img.getRGB(0, 0,W, H, null, 0, W);
         //しきい値より小さい場合: 反転するなら黒,そうでなければ白
        final int lowerColor = invert ? ~0 : 0;
        final int upperColor = ~lowerColor;

        for (int y = 0; y < H; y++) {
            for (int x = 0; x < W; x++) {
                final int c = pixels[W*y + x];
                final int p = (getA(c) + getR(c) + getB(c)) / 3;
                pixels[W*y + x] =
                        (p < shreshold) ? lowerColor : upperColor;
            }
        }
        img.setRGB(0, 0, W, H, pixels, 0, W);
    }

    public static void blur(BufferedImage img, int scale) throws IllegalArgumentException
    {
        if (scale < 0) throw new IllegalArgumentException("`scale` of blur() cannot be Negative-value.");
        if (scale > 30) throw new IllegalArgumentException("`scale` of blur() is too large.");

        final int H = img.getHeight();
        final int W = img.getWidth();
        final int[] pixels = img.getRGB(0, 0,W, H, null, 0, W);
        int newColor;

        for (int y = 0; y < H; y++) {
            for (int x = 0; x < W; x++) {
                // 画像の矩形外の時は現在のピクセル値をそのまま用いる
                if (x-scale < 0 || y-scale < 0 || x+scale >= W || y+scale >= H) {
                    newColor = pixels[W*y + x];
                } else {
                    int sumR = 0, sumG = 0, sumB = 0;   // R,G,B それぞれで平均を取る
                    final int S = (2*scale + 1) * (2*scale + 1);    //面積

                    for (int py = y-scale; py <= y+scale; ++py) {
                        for (int px = x-scale; px <= x+scale; ++px) {
                            final int color = pixels[W*py + px];
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
                pixels[W*y + x] = newColor;
            }
        }
        img.setRGB(0, 0, W, H, pixels, 0, W);
    }

    public static void addRGB(BufferedImage img, int R, int G, int B)
    {
        final int H = img.getHeight();
        final int W = img.getWidth();
        final int[] pixels = img.getRGB(0, 0,W, H, null, 0, W);

        for (int y = 0; y < H; y++) {
            for (int x = 0; x < W; x++) {
                final int c = pixels[W*y + x];
                // 0 ~ 255 の値に収まるように max(), min() を取る
                int newR = Math.min( getR(c)+R, 255 );
                int newG = Math.min( getG(c)+G, 255 );
                int newB = Math.min( getB(c)+B, 255 );
                newR = Math.max( 0, newR );
                newG = Math.max( 0, newG );
                newB = Math.max( 0, newB );

                pixels[W*y + x] = makeRGB(newR, newG, newB);
            }
        }
        img.setRGB(0, 0, W, H, pixels, 0, W);
    }

}
