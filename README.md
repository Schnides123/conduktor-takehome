# Conduktor Take Home

## Introduction
This is my submission for the Conduktor take home assignment. I have implemented a simple Kafka producer and consumer using Spring Boot, Redpanda, and the Kafka client library. The producer sends messages to a Kafka topic, and the consumer reads and processes the messages.

## Prerequisites
- Java 21
- (Optional) ruby for the setup script
- Maven
- Docker
- Docker Compose
- An IDE that can run Java applications

## Setup
1. Clone the repository
2. Navigate to the project root directory
3. Optionally, run the setup script to start the redpanda cluster, install the redpanda CLI, create the "people" topic, and run mvn clean install. I couldn't find a guinnea pig to test this during the time alotted for the take-home, so there is absolutely no guarantee that this thing works or even runs successfully. If you're feeling brave, you can run the script using `ruby setup.rb`
4. Otherwise, start by navigating to the docker-compose directory and running `docker-compose up -d` to start the redpanda cluster
5. From here, either use the UI or install the redpanda CLI using one of the following methods:
    - for mac: `brew install redpanda-data/tap/redpanda`
    - for linux (AMD64/x86): `curl -LO https://github.com/redpanda-data/redpanda/releases/latest/download/rpk-linux-amd64.zip &&
      mkdir -p ~/.local/bin &&
      export PATH="~/.local/bin:$PATH" &&
      unzip rpk-linux-amd64.zip -d ~/.local/bin/`
    - for linux (ARM64): `curl -LO https://github.com/redpanda-data/redpanda/releases/latest/download/rpk-linux-arm64.zip &&
      mkdir -p ~/.local/bin &&
      export PATH="~/.local/bin:$PATH" &&
      unzip rpk-linux-arm64.zip -d ~/.local/bin/`
6. Create the "people" topic using the following command: `rpk topic create people -p 3`
7. install the required packages using `mvn clean install`
8. Run the TakeHomeApplication class in your IDE of choice to start the application

## Usage
The application exposes two REST endpoints on localhost:9090:
- POST /load-people: This endpoint accepts an empty payload, and loads the data in the provided JSON file into the kafka topic.
- GET /topic/{offset}: This endpoint returns a list of people consumed from the kafka topic starting from the provided offset for each partition. It optionally accepts a query parameter "limit" which controls how many people to pull from each partition.

For the sake of convenience, a Swagger UI is available at localhost:9090/swagger-ui.html to interact with the endpoints.