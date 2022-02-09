FROM openjdk:8-jre-slim
MAINTAINER gleiyu

ENV PARAMS=""
ENV TZ Asia/Shanghai
ENV DOCKER true
ENV BREEZE_HOME /opt/breeze

RUN mkdir -p $BREEZE_HOME

WORKDIR $BREEZE_HOME

ADD ./target/breeze-api-*.jar $BREEZE_HOME

EXPOSE 8080 8080

ENTRYPOINT ["sh","-c","java -jar $JAVA_OPTS /opt/breeze/breeze.jar $PARAMS"]