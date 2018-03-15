package ydk.game.sprite;

import java.awt.Graphics2D;
import java.util.Iterator;
import java.util.List;

public abstract class Sprite
{
    protected double x, y;
    private boolean vanished = false;
    private boolean visible = true;

    public abstract void update();

    public abstract void draw(Graphics2D g2d);

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

    public double getX() {
        return this.x;
    }
    public void setX(double x) {
        this.x = x;
    }
    public double addX(double vx) {
        return (this.x += vx);
    }

    public double getY() {
        return this.y;
    }
    public void setY(double y) {
        this.y = y;
    }
    public double addY(double vy) {
        return (this.y += vy);
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

    public static void draw(List<? extends Sprite> list, Graphics2D g2d)
    {
        Iterator<? extends Sprite> it = list.iterator();
        while (it.hasNext())
        {
            Sprite s = it.next();
            if (s.vanished) {
                it.remove();
            }
            else if (s.visible) {
                s.draw(g2d);
            }
        }
    }
}
