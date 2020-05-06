:: exe ::--icon src\main\resources\app-icon.ico ^ 没有图标
jpackage    --name CopyActionDaemon ^
    --type exe ^
    --input  target\ ^
    --dest package ^
    --main-jar CopyActionDaemon-1.0-SNAPSHOT.jar ^
    --vendor ravenxrz ^
    --win-shortcut --win-dir-chooser --win-menu-group "CopyActionDaemon" --win-menu




