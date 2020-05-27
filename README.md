## 1. 初心

CopyTranslator真的是一个非常良心的文献阅读翻译软件，但或许是功能越来越多的原因，CopyTranslator也出现了一些bug。比如在”拖拽翻译“功能上，就可能出现明明已经划词，但是Copytranslator没有反应的情况。

如，在对照模式下：

![](https://cdn.jsdelivr.net/gh/ravenxrz/PicBed/img/8a1yTdsfsE.gif)

这里已经勾选了“拖拽复制”功能，但是却没有效果，同样的情况也发生在专注模式下：

![](https://cdn.jsdelivr.net/gh/ravenxrz/PicBed/img/f8LwkuSXh6.gif)



CopyActionDaemon则是为了解决这个feature bug而开发的。(当然了，CopyTranslaor仍在更新迭代中，所以这个Project很可能要不了多久就作废了)

另外：

> 由于个人不会js的相关技术，所以最终采用了自己比较熟悉的**java**来实现。**但无需用户安装jre环境。**

## 2. 使用

### 效果

拖拽翻译：

![](https://cdn.jsdelivr.net/gh/ravenxrz/PicBed/img/mkabHo3oB1.gif)

双击翻译：

![](https://cdn.jsdelivr.net/gh/ravenxrz/PicBed/img/OtKACSXKJy.gif)

### 如何使用？

进入 [release](https://github.com/ravenxrz/CopyActionDaemon/releases)页面下载对应平台安装包，目前打包了windows平台，下一步拟支持Linux平台，由于无mac电脑，所以无法打mac下的安装包。有兴趣的朋友可自行打包。

**对于无对应安装包的平台，可自行下载jar包。**（这种情况需要自行下载jre）

然后，打开terminal窗口，执行:

```java
cd 到CopyActionDaemon.jar的路径下去
java -jar CopyActionDaemon.jar
```

如：

![image-20200427202255644](https://cdn.jsdelivr.net/gh/ravenxrz/PicBed/img/image-20200427202255644.png)

windows平台可自行下载对应安装包。

**系统托盘可管理是否开启自动复制：**

![image-20200506160201270](https://cdn.jsdelivr.net/gh/ravenxrz/PicBed/img/image-20200506160201270.png)



## 3. 开发过程

> 因为CopyTranslator的这个feature bug，其实弄得我挺糟心的，因为我太懒了，看文献的时候就是不想手动按Ctrl C。所以中途还上网找了两个其它方案--知云和scitranslate，但是它们两个的官网做得实在是一言难尽，且它们固定了pdf软件，而它们的pdf软件不满足我的要求（全屏+自定义背景色）。所以我尝试了小半天，还是回到了CopyTranslator的阵营。
>
> 于是为了解决CopyTranslator的这个”拖拽翻译“的bug，就有了这个小项目。

解决的思路其实很简单，虽然我不懂JS，但是CopyTranslator应该是监听系统clipboard的变化，然后从clipboard中读数据进行翻译。也就是说，我需要做的就仅仅是把给clipboard喂数据就行了。

![image-20200427204951530](https://cdn.jsdelivr.net/gh/ravenxrz/PicBed/img/image-20200427204951530.png)

最开始想着用Qt来做，但是没想到Windows没有提供这样的Api（获取选中的文本），于是只能曲线救国：**监听鼠标的按下和释放过程，在释放的时候模拟Ctrl+C，这样就行把选中的文本复制到剪贴板中了。**结果Qt的模拟按键我没有找到合适的方法，主要还是自己把Qt给忘得差不多了。所以最终选择了java来做。

因为java本身是不支持全局监听鼠标的，所以这里用到了开源库：

- [jnativehook](https://github.com/kwhat/jnativehook)

另外，因为我没有mac的电脑，所以没法测试，mac用户如果需要使用，只需要修改

```java
 // com.ravenxrz.copyactiondaemon.copymethods/CopyRobot.java
public synchronized void triggerCopy(){
    // CONTROL换Command
     robot.keyPress(KeyEvent.VK_CONTROL);
     robot.keyPress(KeyEvent.VK_C);  
     robot.delay(robot.getAutoDelay()); 
     robot.keyRelease(KeyEvent.VK_C);
    // CONTROL换Command
     robot.keyRelease(KeyEvent.VK_CONTROL);
 }                                                 
```



开源许可：GPL V3.0

