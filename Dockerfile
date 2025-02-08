# 使用 jdk 21 作为基础镜像
FROM openjdk:21-jdk-slim

# 安装maven
ARG MAVEN_VERSION=3.9.5
ARG USER_HOME_DIR="/root"
ARG MAVEN_URL="https://apache.osuosl.org/maven/maven-3/${MAVEN_VERSION}/binaries/apache-maven-${MAVEN_VERSION}-bin.zip"

RUN mkdir -p /usr/share/maven /usr/share/maven/ref \
    && curl -o /tmp/apache-maven.zip -fSL "$MAVEN_URL" \
    && unzip /tmp/apache-maven.zip -d /usr/share/maven \
    && rm -f /tmp/apache-maven.zip \
    && ln -s /usr/share/maven/apache-maven-$MAVEN_VERSION/bin/mvn /usr/bin/mvn

# 设置 Maven 环境变量
ENV MAVEN_HOME /usr/share/maven
ENV MAVEN_CONFIG "$USER_HOME_DIR/.m2"

# 设置工作目录
WORKDIR /app

# 将项目代码复制到容器中
COPY . /app

# 运行 Maven 构建
RUN mvn clean install

CMD ["java", "-jar", "target/transaction.jar"]