REM Install JAR
CALL mvn clean install -f "pom.xml"

REM Copy to server plugins directory
CALL copy "target\CamAlert.jar" "C:\Storage\MinecraftProjects\Dev\plugins"

EXIT 0