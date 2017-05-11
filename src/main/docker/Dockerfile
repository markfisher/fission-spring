FROM openjdk:8
VOLUME /tmp
ADD fission-spring-1.0.0.BUILD-SNAPSHOT.jar server.jar
ENV JAVA_OPTS=""
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /server.jar" ]
EXPOSE 8888
