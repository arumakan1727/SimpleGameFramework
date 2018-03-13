package ydk.game.engine;

import java.text.DecimalFormat;

public class FPSCalculator
{
    private static final DecimalFormat DECIMAL_FORMAT  = new DecimalFormat("0.0");
    private static final long          SEC_IN_NANO     = 1_000_000_000;
    private long prevCalcTime;
    private long frameCount = 0;
    private long calcInterval = 0;
    private double actualFPS;

    public FPSCalculator(long startTime)
    {
        this.prevCalcTime = startTime;
    }

    public double calcFPS(final long PERIOD)
    {
        ++frameCount;
        calcInterval += PERIOD;

        // 1秒おきにFPSを再計算する
        if (calcInterval >= SEC_IN_NANO)
        {
            long currentTime = System.nanoTime();
            // 実際の経過時間を測定
            long realElapsedTime = currentTime - prevCalcTime; //単位: ns

            // FPSを計算  nsをsに変換する
            actualFPS = ((double)frameCount / realElapsedTime) * SEC_IN_NANO;

            System.out.println(DECIMAL_FORMAT.format(actualFPS));

            frameCount = 0L;
            calcInterval = 0L;
            prevCalcTime = currentTime;
        }
        return this.actualFPS;
    }
}
