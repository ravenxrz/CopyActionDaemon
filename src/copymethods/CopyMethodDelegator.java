package copymethods;

import listener.GlobalMouseListener;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Raven
 * @version 1.0
 * @date 2020/4/27 17:23
 * 选择文本来复制的方法--判别器
 */
public class CopyMethodDelegator {

    private List<CopyMethod> copyMethods = new ArrayList<>();

    public CopyMethodDelegator(){
        initializeCopyMethodChain();
    }

    /**
     * 初始化，处理链
     * 处理链：
     * DoubleClick --> MouseMove
     */
    private void initializeCopyMethodChain(){
        copyMethods.add(new DoubleClickCopyMethod());
        copyMethods.add(new MouseMoveCopyMethod());
    }

    /**
     * 委托处理
     * @param mouseListener
     */
    public void delegateCopy(GlobalMouseListener mouseListener){
        for(CopyMethod copyMethod : copyMethods){
            if(copyMethod.triggerCopy(mouseListener)){
                System.out.println(copyMethod);
                return;
            }
        }
    }
}
