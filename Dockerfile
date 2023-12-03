FROM openjdk:17-ea-11-jdk-slim
VOLUME /tmp
COPY build/libs/{내 jar 파일 이름} memberService.jar
ENTRYPOINT ["java", "-jar", "memberService.jar"]
