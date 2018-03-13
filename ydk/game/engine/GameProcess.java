package ydk.game.engine;

import java.awt.Graphics;

public abstract class GameProcess
{
    public abstract void initialize();

    public abstract void update();

    public abstract void render(Graphics g);
}
