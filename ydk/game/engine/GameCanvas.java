package ydk.game.engine;

import java.awt.Canvas;
import java.awt.Dimension;

public class GameCanvas extends Canvas
{
    public GameCanvas(int width, int height)
    {
        Dimension dimension = new Dimension(width, height);
        this.setPreferredSize(dimension);
        this.setMaximumSize(dimension);
        this.setMinimumSize(dimension);
        this.setFocusable(true);
    }

    public void setBuffering(final int numBuffers) throws IllegalArgumentException
    {
        try {
            this.createBufferStrategy(numBuffers);
        } catch (IllegalStateException e) {
            e.printStackTrace();
            System.err.println("Error : window must be visible.");
            System.exit(1);
        }
        this.setIgnoreRepaint(true);
    }

}
