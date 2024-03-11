FROM openjdk:17-ea-11-jdk-slim
VOLUME /tmp
COPY build/libs/*.jar memberService.jar
ENTRYPOINT ["java", "-jar", "memberService.jar"]
