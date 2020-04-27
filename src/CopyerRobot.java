import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * 复制器
 */
class CopyerRobot{
    private Robot robot;
    {
        try {
            robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }


    /**
     * 触发copy action
     * @return 触发是时候成功
     *          true 成功
     *          false 失败
     */
    public boolean triggerCopy(){
        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_C);
        // 延迟100ms
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
            return false;
        }
        robot.keyRelease(KeyEvent.VK_C);
        robot.keyRelease(KeyEvent.VK_CONTROL);
        return true;
    }
}
