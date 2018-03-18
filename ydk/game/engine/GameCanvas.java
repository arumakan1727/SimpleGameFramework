package ydk.game.engine;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;

public class GameCanvas
        extends Canvas implements BufferingRenderer
{
    public GameCanvas(int width, int height)
    {
        Dimension dimension = new Dimension(width, height);
        this.setPreferredSize(dimension);
        this.setMaximumSize(dimension);
        this.setMinimumSize(dimension);
        this.setFocusable(true);
    }

    @Override
    public void setDoubleBuffering()
    {
        try {
            this.createBufferStrategy(2);
        } catch (IllegalStateException e) {
            e.printStackTrace();
            System.err.println("Error : window must be visible.");
            System.exit(1);
        }
        this.setIgnoreRepaint(true);
    }

    @Override
    public Graphics2D getRenderer()
    {
        final BufferStrategy strategy = this.getBufferStrategy();

        if (!strategy.contentsLost()) {
            return (Graphics2D) strategy.getDrawGraphics();
        } else {
            System.out.println("BufferStrategy lost");
            return null;
        }
    }

    @Override
    public void showBuffer()
    {
        this.getBufferStrategy().show();
        Toolkit.getDefaultToolkit().sync();
    }
}
