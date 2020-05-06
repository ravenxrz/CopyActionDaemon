package initiator;

/**
 * @author Raven
 * @version 1.0
 * @date 2020/5/6 12:59
 */
public class Initiator {
    public static void initialize(){
        LoggerInitiator.initialize();
        GlobalScreenHookInitiator.initialize();
        SystemTrayInitiator.initialize();
    }
}
