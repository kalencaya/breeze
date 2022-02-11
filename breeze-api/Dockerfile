FROM openjdk:8-jdk-alpine
MAINTAINER gleiyu

ENV MYSQL_URL="jdbc:mysql://mysql:3306/breeze"
ENV MYSQL_USERNAME=root
ENV MYSQL_PASSWORD=123456
ENV REDIS_HOST=redis
ENV REDIS_PORT=6379
ENV REDIS_PASSWORD=123456

ENV TZ Asia/Shanghai
ENV BREEZE_HOME /opt/breeze

ENV JAVA_OPTS ""
ENV PARAMS ""

RUN mkdir -p $BREEZE_HOME

WORKDIR $BREEZE_HOME

ADD ./target/breeze-api*.jar $BREEZE_HOME/breeze.jar

EXPOSE 8080 8080

ENTRYPOINT ["sh","-c","java -jar ${JAVA_OPTS} /opt/breeze/breeze.jar --spring.profiles.active=docker ${PARAMS}"]