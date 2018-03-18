package ydk.game.engine;

import java.awt.Graphics2D;

public interface BufferingRenderer
{
    void setDoubleBuffering();

    Graphics2D getRenderer();

    void showBuffer();
}
