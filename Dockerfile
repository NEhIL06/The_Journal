FROM maven:3.9.8-eclipse-temurin-21 AS build
WORKDIR /build

# Copy only module first to leverage Docker layer caching
COPY journaling/pom.xml journaling/pom.xml
COPY journaling/src journaling/src

WORKDIR /build/journaling
RUN mvn -B -DskipTests package

# Runtime image
FROM eclipse-temurin:21-jre
WORKDIR /app

COPY --from=build /build/journaling/target/*.jar /app/app.jar
EXPOSE 8080

# Allow custom JVM options via JAVA_OPTS. Bind to Render's provided PORT.
ENV JAVA_OPTS=""
CMD sh -c 'java $JAVA_OPTS -Dserver.port=${PORT:-8080} -jar /app/app.jar'

