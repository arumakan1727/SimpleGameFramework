package ydk.game.engine;

import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

public class GamePanel
        extends JPanel implements BufferingRenderer
{
    private BufferedImage buffer = null;
    private Graphics2D g2d = null;

    public GamePanel(int width, int height)
    {
        final Dimension dimension = new Dimension(width, height);
        this.setPreferredSize(dimension);
        this.setSize(dimension);
        this.setFocusable(true);
    }

    @Override
    public void setDoubleBuffering()
    {
        buffer = new BufferedImage(
                this.getWidth(),
                this.getHeight(),
                BufferedImage.TYPE_INT_RGB);
        this.setDoubleBuffered(true);
    }

    @Override
    public Graphics2D getRenderer()
    {
        g2d = buffer.createGraphics();
        return g2d;
    }

    @Override
    public void showBuffer()
    {
        // this#getRenderer の buffer の g2d のリソースを開放
        if (g2d != null) g2d.dispose();

        // 自信の Graphics2D を取得
        g2d = (Graphics2D) this.getGraphics();
        g2d.drawImage(buffer, 0, 0, null);
        g2d.dispose();
        Toolkit.getDefaultToolkit().sync();
    }

}
