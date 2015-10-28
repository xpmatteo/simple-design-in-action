
mvn package -DskipTests=true
java -cp target/classes;"target/dependency/*" it.xpug.woodysmart.main.Main
