package copymethods;

import listener.GlobalMouseListener;

import java.awt.*;

/**
 * @author Raven
 * @version 1.0
 * @date 2020/4/27 17:37
 * 鼠标移动较长距离来触发copy
 */
public class MouseMoveCopyMethod extends CopyMethod {

    /* the threshold of position delta for trigger copy action*/
    private double threshold;

    public MouseMoveCopyMethod() {
        threshold = 50;
    }

    @Override
    boolean triggerCopy(GlobalMouseListener globalMouseListener) {
        Point start = globalMouseListener.getStartPoint();
        Point end = globalMouseListener.getEndPoint();
        return sendCopyAction(start,end);
    }

    /**
     * 尝试处理copy动作
     * @param startPoint
     * @param endPoint
     * @return
     */
    private boolean sendCopyAction(Point startPoint, Point endPoint){
        double deltex = startPoint.getX() - endPoint.getX();
        double deltay = startPoint.getY() - endPoint.getY();
        if(Math.pow(deltex,2) + Math.pow(deltay,2) >= Math.pow(threshold,2)) {
            copyerRobot.triggerCopy();
            return true;
        }else{
            return false;
        }
    }

    public double getThreshold() {
        return threshold;
    }

    public void setThreshold(double threshold) {
        this.threshold = threshold;
    }

}
