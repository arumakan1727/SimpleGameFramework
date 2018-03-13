package ydk.game.sprite;

import java.awt.Graphics;
import java.util.Iterator;
import java.util.List;

public abstract class Sprite
{
    protected double x, y;
    private boolean vanished = false;
    private boolean visible = true;

    public abstract void update();

    public abstract void draw(Graphics g);

    public boolean isVanished() {
        return this.vanished;
    }
    public void vanish() {
        this.vanished = true;
        this.visible = false;
    }

    public boolean isVisible() {
        return this.visible;
    }
    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public static void update(List<? extends Sprite> list)
    {
        Iterator<? extends Sprite> it = list.iterator();
        while (it.hasNext())
        {
            Sprite s = it.next();
            if (s.vanished) {
                it.remove();
            } else {
                s.update();
            }
        }
    }

    public static void draw(List<? extends Sprite> list, Graphics g)
    {
        Iterator<? extends Sprite> it = list.iterator();
        while (it.hasNext())
        {
            Sprite s = it.next();
            if (s.vanished) {
                it.remove();
            }
            else if (s.visible) {
                s.draw(g);
            }
        }
    }
}
