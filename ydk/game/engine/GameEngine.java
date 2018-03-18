package ydk.game.engine;

import javax.swing.JFrame;
import java.awt.Component;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;

public class GameEngine implements Runnable
{
    private final JFrame window;
    private final BufferingRenderer renderer;
    private final GameProcess process;

    private boolean running = false;
    private int     fps     = 60;
    private long period = (long)(1.0 / fps * 1_000_000_000);
    private Thread  gameThread;

    public GameEngine(BufferingRenderer renderer, GameProcess proc)
            throws IllegalArgumentException
    {
        this.window = new JFrame();
        this.renderer = renderer;
        this.process = proc;

        if (!(renderer instanceof Component)) {
            throw new IllegalArgumentException("Instance of BufferingRenderer is Not Component.");
        }

        this.window.addNotify();
        this.window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.window.add((Component) this.renderer);
        this.window.pack();
        this.window.setResizable(false);
        this.window.setLocationRelativeTo(null);
        this.window.setVisible(true);
        this.window.setFocusable(true);
        this.window.requestFocus();

        this.renderer.setDoubleBuffering();

    }

    @Override
    public void run()
    {
        long currentTime, beforeTime, timeDiff, sleepTime;
        long overSleepTime = 0L;
        int noDelays = 0;

        beforeTime = System.nanoTime();
//        FPSCalculator fpsCalculator = new FPSCalculator(beforeTime);

        while (this.running)
        {
            this.gameUpdate();

            currentTime = System.nanoTime();
            timeDiff = currentTime - beforeTime;
            // 前回のフレームの休止時間誤差も引いておく
            sleepTime = (this.period - timeDiff) - overSleepTime;

            if (sleepTime > 0) { // 休止時間が取れる場合
                try {
                    Thread.sleep(sleepTime / 1_000_000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // sleep() の誤差
                overSleepTime = (System.nanoTime() - currentTime) - sleepTime;
            } else {
                // 状態更新・レンダリングで時間を使い切ってしまい,休止時間が撮れない場合
                overSleepTime = 0L;
                if (++noDelays >= 16){
                    Thread.yield();
                    noDelays = 0;
                }
            }
            beforeTime = System.nanoTime();
//            fpsCalculator.calcFPS(this.period);
        }
    }

    public void start()
    {
        this.running = true;
        if (this.gameThread == null)
        {
            this.gameThread = new Thread(this);
        }
        this.process.initialize();
        gameThread.start();
        System.out.println("GameLoop start");
    }

    public void stop()
    {
        this.running = false;
    }

    public void setWindowTitle(final String title)
    {
        this.window.setTitle(title);
    }

    public JFrame getWindow()
    {
        return this.window;
    }

    public BufferingRenderer getBufferingRenderer()
    {
        return this.renderer;
    }

    public void setFps(int fps) throws IllegalArgumentException
    {
        if (fps <= 0)
            throw (new IllegalArgumentException());
        this.fps = fps;
        this.period = (long)(1.0 / fps * 1_000_000_000);
    }

    public int getFps()
    {
        return this.fps;
    }

    public long getPeriod()
    {
        return this.period;
    }

    private void gameUpdate()
    {
        process.update();

        final Graphics2D g2d = renderer.getRenderer();

        if (g2d != null) {
            process.render(g2d);
            g2d.dispose();
        }
        renderer.showBuffer();
    }
}
