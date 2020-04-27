import org.jnativehook.mouse.NativeMouseEvent;

import java.awt.*;

/**
 * @author Raven
 * @version 1.0
 * @date 2020/4/27 15:43
 */
class GlobalMouseListener extends GlobalMouseListenerAdapter {

    private CopyerRobot copyerRobot = new CopyerRobot();
    private Point startPoint,endPoint;
    /* the threshold for trigger copy action*/
    private double threshold;

    public GlobalMouseListener() {
        threshold = 50;
    }

    @Override
    public void nativeMousePressed(NativeMouseEvent nativeEvent) {
        startPoint = nativeEvent.getPoint();
    }

    @Override
    public void nativeMouseReleased(NativeMouseEvent nativeEvent) {
       endPoint = nativeEvent.getPoint();
       sendCopyAction();
    }

    private void sendCopyAction(){
        double deltex = startPoint.getX() - endPoint.getX();
        double deltay = startPoint.getY() - endPoint.getY();
        if(Math.pow(deltex,2) + Math.pow(deltay,2) >= Math.pow(threshold,2)) {
            copyerRobot.triggerCopy();
        }
    }

    public double getThreshold() {
        return threshold;
    }

    public void setThreshold(double threshold) {
        this.threshold = threshold;
    }

}
