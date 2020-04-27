package copymethods;

import listener.GlobalMouseListener;

/**
 * @author Raven
 * @version 1.0
 * @date 2020/4/27 17:31
 */
public abstract class CopyMethod {
    protected CopyRobot copyerRobot = CopyRobot.getInstance();

    /**
     * 处理copy的动作
     * @param globalMouseListener
     * @return 处理完成，return true
     *         无法处理，return false
     */
    abstract boolean triggerCopy(GlobalMouseListener globalMouseListener);
}
