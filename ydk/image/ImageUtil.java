package ydk.image;

import java.awt.image.BufferedImage;

public class ImageUtil
{
    private ImageUtil() {}

    public static int getA(int color) {
        return (color >>> 24);
    }
    public static int getR(int color) {
        return (color >>> 16) & 0xff;
    }
    public static int getG(int color) {
        return (color >>> 8)  & 0xff;
    }
    public static int getB(int color) {
        return (color & 0xff);
    }

    public static int makeRGB(int r, int g, int b) {
        return (0xff00_0000 | r<<16 | g<<8 | b);
    }
    public static int makeARGB(int a, int r, int g, int b) {
        return (a<<24 | r<<16 | g<<8 | b);
    }

    public static BufferedImage imageCopy(final BufferedImage img)
    {
        BufferedImage cp = new BufferedImage(img.getWidth(), img.getHeight(), img.getType());
        cp.setData(img.getData());
        return cp;
    }
}
