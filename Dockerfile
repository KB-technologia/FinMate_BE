# ---------- 1단계: 빌드 ----------
FROM eclipse-temurin:17-jdk AS builder
WORKDIR /build
COPY gradlew .
COPY gradle ./gradle

RUN chmod +x gradlew

COPY build.gradle settings.gradle gradle.properties* ./

RUN ./gradlew dependencies || true

COPY src ./src

RUN ./gradlew clean war -x test

# ---------- 2단계: 런타임 ----------
FROM tomcat:9.0-jdk17-temurin

RUN rm -rf /usr/local/tomcat/webapps/*

ENV TZ=Asia/Seoul \
    JAVA_OPTS="-Xms512m -Xmx1024m -Dfile.encoding=UTF-8 -Duser.timezone=Asia/Seoul" \
    LIVENESS_PATH="/healthz/liveness" \
    READINESS_PATH="/healthz" \
    HEALTHCHECK_PORT="8080"


RUN apt-get update && apt-get install -y curl && rm -rf /var/lib/apt/lists/*

# Liveness: 단순 서버 동작 여부 확인
HEALTHCHECK --interval=10s --timeout=3s --retries=3 \
  CMD curl -fsS "http://localhost:${HEALTHCHECK_PORT}${LIVENESS_PATH}" || exit 1

COPY --from=builder /build/build/libs/*.war /usr/local/tomcat/webapps/ROOT.war

EXPOSE 8080


