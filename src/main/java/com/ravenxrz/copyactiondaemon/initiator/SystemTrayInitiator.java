package com.ravenxrz.copyactiondaemon.initiator;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 * @author Raven
 * @version 1.0
 * @date 2020/5/6 13:02
 */
public class SystemTrayInitiator {
    /**
     * 无状态Item
     */
    private static MenuItem exitItem = new MenuItem("退出");
    /**
     * 有状态item
     */
    private static CheckboxMenuItem copyActionItem = new CheckboxMenuItem("自动复制");
    private static CheckboxMenuItem textFormatItem = new CheckboxMenuItem("文本格式化");
    // 自动复制
    private static ActionMenuClickListener copyActionListener;
    // 剪切板（文本格式化用）
    private static ActionMenuClickListener clipboardActionListener;


    public static void initialize() {
        // 初始化系统托盘
        createSystemTray();
    }

    /*
     * 添加系统托盘
     */
    private static void createSystemTray() {
        if (SystemTray.isSupported()) {
            // 获取当前平台的系统托盘
            SystemTray tray = SystemTray.getSystemTray();
            // 加载一个图片用于托盘图标的显示
            Image image = Toolkit.getDefaultToolkit().getImage(
                    SystemTrayInitiator.class.getClassLoader().getResource("tray-icon.png"));
            PopupMenu popupMenu = createPopupMenuItems();
            TrayIcon trayIcon = new TrayIcon(image, "CopyActionDaemon", popupMenu);
            // 托盘图标自适应尺寸
            trayIcon.setImageAutoSize(true);
            // 添加托盘图标到系统托盘
            try {
                tray.add(trayIcon);
            } catch (AWTException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("当前系统不支持系统托盘");
        }
    }

    /**
     * 创建点击图标时的弹出菜单
     *
     * @return
     */
    private static PopupMenu createPopupMenuItems() {
        PopupMenu popupMenu = new PopupMenu();
        // 事件处理
        SystemTrayPopupEventProcess systemTrayPopupEventProcess = new SystemTrayPopupEventProcess();
        // 默认打开copyAction,写死了，后期可能采用配置文件形式
        copyActionItem.setState(true);
        copyActionItem.addItemListener(systemTrayPopupEventProcess);
        textFormatItem.setState(true);
        textFormatItem.addItemListener(systemTrayPopupEventProcess);
        exitItem.addActionListener(systemTrayPopupEventProcess);
        popupMenu.add(copyActionItem);
        popupMenu.add(textFormatItem);
        popupMenu.add(exitItem);
        return popupMenu;
    }


    /**
     * 系统托盘menuitem事件处理类
     */
    private static class SystemTrayPopupEventProcess implements ActionListener, ItemListener {

        /**
         * 无状态item 处理
         * @param e
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            Object source = e.getSource();
            if(source == exitItem){
                // 是否需要做一些回收工作？
                System.exit(0);
            }
        }


        /**
         * 有状态Item 处理
         * @param e
         */
        @Override
        public void itemStateChanged(ItemEvent e) {
            Object source = e.getSource();
            if(source == copyActionItem){
                copyAction(e.getStateChange());
            }else if(source == textFormatItem){
                textFormatAction(e.getStateChange());
            }
        }

        /*
         * 处理copyAction打开或关闭事件
         */
        private void copyAction(int state){
            if(copyActionListener != null){
                copyActionListener.onClick(state == ItemEvent.SELECTED);
            }
        }

        /**
         * 文本格式化打开或关闭
         * @param state
         */
        private void textFormatAction(int state){
            if(clipboardActionListener != null){
                clipboardActionListener.onClick(state == ItemEvent.SELECTED);
            }
        }
    }

    /**
     * CopyAction的MenuItemListener
     * 或者不用回调，用中介者模式？
     * 托盘触发->中介者->事件分发器？
     * 或者通过全局状态，方便读取配置文件
     */
    public interface ActionMenuClickListener {
        void onClick(boolean selected);
    }

    public static void setCopyActionListener(ActionMenuClickListener copyActionListener) {
        SystemTrayInitiator.copyActionListener = copyActionListener;
    }

    public static void setClipboardActionListener(ActionMenuClickListener clipboardActionListener) {
        SystemTrayInitiator.clipboardActionListener = clipboardActionListener;
    }
}
