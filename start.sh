cd /app/
mvn package
java -Djava.security.egd=file:/dev/./urandom -jar /app/target/store-1.0.0-SNAPSHOT.jar