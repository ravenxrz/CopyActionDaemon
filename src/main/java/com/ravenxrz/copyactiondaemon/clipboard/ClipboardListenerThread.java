package com.ravenxrz.copyactiondaemon.clipboard;
import com.ravenxrz.copyactiondaemon.copymethods.CopyRobot;
import com.ravenxrz.copyactiondaemon.initiator.SystemTrayInitiator;

import java.awt.*;
import java.awt.datatransfer.*;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ClipboardListenerThread extends Thread implements ClipboardOwner {
    // 系统剪切板
    private final Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
    // blocker用于线程保活
    private final Blocker blocker = new Blocker();
    // 程序暂停
    private volatile boolean running = true;
    // 动态等待时间
    private long waitTime = 50;
    // 跳过本次变化监听
    private boolean ignoreThisTime = false;


    public ClipboardListenerThread() {
        setName("Clipboard Thread");
    }

    @Override
    public void run() {
        init();
        blocker.keepAlive();
        destroy();
        Logger.getLogger(this.getClass().getName()).info("ClipBoard exit");
    }

    private void init() {
        // 初始化剪切板监听
        Transferable trans = clipboard.getContents(this);
        clipboard.setContents(trans, this);
        // 绑定系统托盘点击
        SystemTrayInitiator.setClipboardActionListener(this::setRun);
    }

    private void destroy() {
    }

    @Override
    public void lostOwnership(Clipboard c, Transferable t) {
        Transferable contents;
        // 循环require com.ravenxrz.copyactiondaemon.clipboard owner
        boolean required = false;
        final long minWaitTime = 50;
        final long maxWaitTime = 3000;
        while (!required) {
            try {
                //waiting e.g for loading huge elements like word's etc.
                Thread.sleep(waitTime);
                contents = clipboard.getContents(null);
                clipboard.setContents(contents, this);
                required = true;
                // 忽略条件只能在成为剪切板owner后判定，否则无法继续监听剪切板
                if (!running) {
                    break;
                }
                if (ignoreThisTime) {
                    ignoreThisTime = false;
                    break;
                }
                processClipboard(contents);
            } catch (Exception e) {
                if (waitTime < maxWaitTime) {
                    waitTime += 100;  // 增加100ms等待时间
                }
                e.printStackTrace();
                Logger.getLogger(this.getClass().getName()).log(Level.WARNING, e.getLocalizedMessage(), e);
            } finally {
                if (waitTime > minWaitTime) {
                    waitTime -= 10;   // 减少10ms等待时间
                }
            }
        }
    }

    private void processClipboard(Transferable t) throws IOException, UnsupportedFlavorException {
        if (t.isDataFlavorSupported(DataFlavor.stringFlavor)) {
            String paste = (String) t.getTransferData(DataFlavor.stringFlavor);
            // 文本格式化
            String formatText = RedundantLineBreakProcessor.format(paste);
            // 必须设置这个标志，不然会引起无限loop
            ignoreThisTime = true;
            CopyRobot.getInstance().copyText(formatText);
        }
    }


    public void setRun(boolean run) {
        if (run) {
            resumeRun();
        } else {
            pause();
        }
    }

    private void pause() {
        running = false;
    }

    private void resumeRun() {
        running = true;
    }

    public void exit() {
        blocker.dead();
    }

    /**
     * 阻塞器，用于线程保活
     */
    private static class Blocker {
        public synchronized void keepAlive() {
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        public synchronized void dead() {
            this.notify();
        }
    }
}