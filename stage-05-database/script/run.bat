@ECHO OFF

mvn package -DskipTests=true
%JAVA_HOME%\bin\java -cp target/classes;target/dependency/* it.xpug.woodysmart.main.Main
