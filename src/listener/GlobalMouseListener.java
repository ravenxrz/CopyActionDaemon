package listener;

import copymethods.MouseEventDispatcher;
import org.jnativehook.mouse.NativeMouseEvent;

/**
 * @author Raven
 * @version 1.0
 * @date 2020/4/27 15:43
 */
public class GlobalMouseListener extends GlobalMouseListenerAdapter {

    private MouseEventDispatcher mouseEventDispatcher = new MouseEventDispatcher();

    @Override
    public void nativeMousePressed(NativeMouseEvent nativeEvent) {
        mouseEventDispatcher.pressEventDispatch(nativeEvent);
    }

    @Override
    public void nativeMouseReleased(NativeMouseEvent nativeEvent) {
        mouseEventDispatcher.releaseEventDispatch(nativeEvent);
    }
}
