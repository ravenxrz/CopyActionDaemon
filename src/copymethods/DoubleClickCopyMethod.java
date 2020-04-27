package copymethods;

import listener.GlobalMouseListener;

/**
 * @author Raven
 * @version 1.0
 * @date 2020/4/27 17:35
 * 快速双击触发copy
 */
public class DoubleClickCopyMethod extends CopyMethod{

    /* 时间间隔 单位ms*/
    private long threshold;

    public DoubleClickCopyMethod() {
        this.threshold = 300;    // default: 300ms
    }

    public long getThreshold() {
        return threshold;
    }

    public void setInterval(long threshold) {
        this.threshold = threshold;
    }

    @Override
    boolean triggerCopy(GlobalMouseListener globalMouseListener) {
        // double click interval
        long interval = globalMouseListener.getClickInterval();
        return sendCopyAction(interval);
    }

    private boolean sendCopyAction(long interval){
        if(interval < threshold){
            copyerRobot.triggerCopy();
            return true;
        }else{
            return false;
        }
    }
}
