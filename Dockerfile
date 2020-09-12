FROM maven:3.5.2-jdk-8-alpine
RUN mkdir /app
WORKDIR /app
COPY . /app/
EXPOSE 8080