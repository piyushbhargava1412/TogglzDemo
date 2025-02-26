# Togglz Demo

## Local Setup

### AWS setup
Firstly we must configure AWS for local run

```bash
  aws configure --profile local-dev
```
use the below suggested or whatever you want to set, make sure the same has also been set in the application properties. These also might have to be exported on the terminal on which you run the aws cli commands
```bash
  export AWS_ACCESS_KEY_ID=test
  export AWS_SECRET_ACCESS_KEY=test
  export AWS_DEFAULT_REGION=us-east-1
```

To check if the profile has been set correctly,
```bash
  aws configure list --profile local-dev
```

### Start the docker containers
```bash
  docker-compose down && docker-compose up -d
```

### Create DynamoDB Table
```shell
    sh scripts/create-table.sh
```

### Start application
In separate terminals, you could start multiple instances of the application

**Terminal 1**
```shell
    ./gradlew bootRun --args="--server.port=8081"
```

**Terminal 2**
```shell
    ./gradlew bootRun --args="--server.port=8082"
```

**Terminal 3**
```shell
    ./gradlew bootRun --args="--server.port=8083"
```

### To change the feature state in DB
Set to true
```shell
    sh scripts/update-table.sh DUMMY_TOGGLE_1 true
```

### Check the status from all instances
Run the below commands and check logs for each instance whether they fetch the state from store or from cache
```shell
    curl -X GET http://localhost:8080/feature-toggle/DUMMY_TOGGLE_1
    curl -X GET http://localhost:8081/feature-toggle/DUMMY_TOGGLE_1
    curl -X GET http://localhost:8082/feature-toggle/DUMMY_TOGGLE_1
```
