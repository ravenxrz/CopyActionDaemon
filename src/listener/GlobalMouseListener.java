package listener;

import copymethods.CopyMethodDelegator;
import org.jnativehook.mouse.NativeMouseEvent;

import java.awt.*;

/**
 * @author Raven
 * @version 1.0
 * @date 2020/4/27 15:43
 */
public class GlobalMouseListener extends GlobalMouseListenerAdapter {

    private CopyMethodDelegator copyMethodDelegator = new CopyMethodDelegator();

    private Point startPoint, endPoint;
    // 双击的计算方案不太好，健壮性不行
    private long[] clickTimeRecord = new long[2];
    private int clickTimeArrIdx = -1;

    public GlobalMouseListener() {
        startPoint = endPoint = new Point(0, 0);
    }

    /**
     * For MouseMove
     *
     * @return
     */
    public Point getStartPoint() {
        return startPoint;
    }

    public Point getEndPoint() {
        return endPoint;
    }

    /**
     * for double click
     * @return
     */
    public long getClickInterval() {
        return Math.abs(clickTimeRecord[0] - clickTimeRecord[1]);
    }

    @Override
    public void nativeMousePressed(NativeMouseEvent nativeEvent) {
        startPoint = nativeEvent.getPoint();
    }

    @Override
    public void nativeMouseReleased(NativeMouseEvent nativeEvent) {
        // set endpoint for mouse move
        endPoint = nativeEvent.getPoint();
        // set time arr to calculate interval for double click
        clickTimeArrIdx = (++clickTimeArrIdx) & 1;
        clickTimeRecord[clickTimeArrIdx] = System.currentTimeMillis();
        // delegate
        copyMethodDelegator.delegateCopy(this);
    }
}
