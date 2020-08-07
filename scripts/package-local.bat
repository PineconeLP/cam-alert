REM Package JAR
call mvn clean package -f "pom.xml"

REM Copy to server plugins directory
call copy "target\CamAlert.jar" "A:\MinecraftProjects\Dev\plugins"