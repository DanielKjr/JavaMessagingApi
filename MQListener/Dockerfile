
FROM eclipse-temurin:21-jdk-jammy as deps
WORKDIR /build

COPY --chmod=0755 mvnw mvnw
COPY .mvn/ .mvn/
COPY pom.xml pom.xml

# cache dependencies
RUN --mount=type=cache,target=/root/.m2 \
    --mount=type=secret,id=maven_settings,target=/root/.m2/settings.xml \
    ./mvnw dependency:go-offline -DskipTests


# build
FROM eclipse-temurin:21-jdk-jammy as package
WORKDIR /build

# copy wrapper and poms
COPY --chmod=0755 mvnw mvnw
COPY .mvn/ .mvn/
COPY pom.xml pom.xml
COPY src src

# use cached dependencies
RUN --mount=type=cache,target=/root/.m2 \
    --mount=type=secret,id=maven_settings,target=/root/.m2/settings.xml \
    ./mvnw package -DskipTests && \
    mv target/*.jar target/app.jar


# extract layers
FROM package as extract
WORKDIR /build
RUN java -Djarmode=layertools -jar target/app.jar extract --destination target/extracted


# create mirror
FROM eclipse-temurin:21-jre-jammy AS final

ARG UID=10001
RUN adduser \
    --disabled-password \
    --gecos "" \
    --home "/nonexistent" \
    --shell "/sbin/nologin" \
    --no-create-home \
    --uid "${UID}" \
    appuser
USER appuser

COPY --from=extract /build/target/extracted/dependencies/ ./
COPY --from=extract /build/target/extracted/spring-boot-loader/ ./
COPY --from=extract /build/target/extracted/snapshot-dependencies/ ./
COPY --from=extract /build/target/extracted/application/ ./

EXPOSE 8001

ENTRYPOINT [ "java", "org.springframework.boot.loader.launch.JarLauncher" ]

















# syntax=docker/dockerfile:1.4
#FROM maven:3.9.9-eclipse-temurin-21 AS build
#
## Use Maven settings from secret
#RUN --mount=type=secret,id=maven_settings,target=/root/.m2/settings.xml \
#    mvn -B dependency:go-offline
#
#WORKDIR /app
#COPY . .
#RUN --mount=type=secret,id=maven_settings,target=/root/.m2/settings.xml \
#    mvn clean package
#
#FROM eclipse-temurin:21-jdk-jammy as deps
#
#WORKDIR /build
#
#
#COPY --chmod=0755 mvnw mvnw
#COPY .mvn/ .mvn/
#COPY pom.xml .
##RUN ./mvnw dependency:go-offline -DskipTests
#
##RUN --mount=type=secret,id=maven_settings,target=/root/.m2/settings.xml ./mvnw dependency:go-offline -DskipTests
##RUN --mount=type=bind,source=pom.xml,target=pom.xml \
##    --mount=type=cache,target=/root/.m2 ./mvnw dependency:go-offline -DskipTests
#RUN --mount=type=bind,source=pom.xml,target=pom.xml \
#    --mount=type=cache,target=/root/.m2 \
#    --mount=type=secret,id=maven_settings,target=/root/.m2/settings.xml \
#    ./mvnw package -DskipTests && \
#    mv target/$(./mvnw help:evaluate -Dexpression=project.artifactId -q -DforceStdout)-$(./mvnw help:evaluate -Dexpression=project.version -q -DforceStdout).jar target/app.jar
#
#
#FROM deps as package
#
#WORKDIR /build
#
#
#
#COPY ./src src/
#RUN --mount=type=bind,source=pom.xml,target=pom.xml \
#    --mount=type=cache,target=/root/.m2 \
#    ./mvnw package -DskipTests && \
#    mv target/$(./mvnw help:evaluate -Dexpression=project.artifactId -q -DforceStdout)-$(./mvnw help:evaluate -Dexpression=project.version -q -DforceStdout).jar target/app.jar
#
#
#FROM package as extract
#
#WORKDIR /build
#
#RUN java -Djarmode=layertools -jar target/app.jar extract --destination target/extracted
#
#
#FROM eclipse-temurin:21-jre-jammy AS final
#
#ARG UID=10001
#RUN adduser \
#    --disabled-password \
#    --gecos "" \
#    --home "/nonexistent" \
#    --shell "/sbin/nologin" \
#    --no-create-home \
#    --uid "${UID}" \
#    appuser
#USER appuser
#
#COPY --from=extract build/target/extracted/dependencies/ ./
#COPY --from=extract build/target/extracted/spring-boot-loader/ ./
#COPY --from=extract build/target/extracted/snapshot-dependencies/ ./
#COPY --from=extract build/target/extracted/application/ ./
#
#EXPOSE 8080
#
#ENTRYPOINT [ "java", "org.springframework.boot.loader.launch.JarLauncher" ]






















#
#FROM maven:3.9.6-eclipse-temurin-21 AS mqloggingclient-builder
#
#WORKDIR /build
#
#COPY MQLoggingClient ./MQLoggingClient
#
#RUN mvn -f MQLoggingClient/pom.xml clean install -DskipTests
#
## Stage 2: Build MQListener using MQLoggingClient from local repo
#FROM maven:3.9.6-eclipse-temurin-21 AS mqlistener-builder
#
#WORKDIR /build
#
#COPY MQListener ./MQListener
## Copy installed MQLoggingClient to local Maven repo in this stage
#COPY --from=mqloggingclient-builder /root/.m2 /root/.m2
#
#RUN mvn -f MQListener/pom.xml clean package -DskipTests
#
## Stage 3: Package and extract (adjust paths as per your original Dockerfile)
#FROM mqlistener-builder AS package
#
#WORKDIR /build/MQListener
#
#RUN mvn package -DskipTests && \
#    mv target/$(mvn help:evaluate -Dexpression=project.artifactId -q -DforceStdout)-$(mvn help:evaluate -Dexpression=project.version -q -DforceStdout).jar target/app.jar
#
#FROM package AS extract
#
#WORKDIR /build/MQListener
#
#RUN java -Djarmode=layertools -jar target/app.jar extract --destination target/extracted
#
## Final runtime image
#FROM eclipse-temurin:21-jre-jammy AS final
#
#ARG UID=10001
#RUN adduser --disabled-password --gecos "" --home "/nonexistent" --shell "/sbin/nologin" --no-create-home --uid "${UID}" appuser
#
#USER appuser
#
#WORKDIR /app
#
#COPY --from=extract /build/MQListener/target/extracted/dependencies/ ./
#COPY --from=extract /build/MQListener/target/extracted/spring-boot-loader/ ./
#COPY --from=extract /build/MQListener/target/extracted/snapshot-dependencies/ ./
#COPY --from=extract /build/MQListener/target/extracted/application/ ./
#
#EXPOSE 8080
#
#ENTRYPOINT ["java", "org.springframework.boot.loader.launch.JarLauncher"]
