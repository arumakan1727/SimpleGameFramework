package ydk.game.engine;

import java.awt.Graphics2D;

public interface GameProcess
{
    void initialize();

    void update();

    void render(Graphics2D g2d);
}
