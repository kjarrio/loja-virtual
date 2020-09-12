cd /app/
mvn package
java -Djava.security.egd=file:/dev/./urandom -jar /app/target/store-0.0.1-SNAPSHOT.jar