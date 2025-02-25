#!/bin/bash
echo "Creating DynamoDB table..."
aws dynamodb create-table \
    --table-name feature_toggles \
    --attribute-definitions AttributeName=featureName,AttributeType=S \
    --key-schema AttributeName=featureName,KeyType=HASH \
    --billing-mode PAY_PER_REQUEST \
    --region us-east-1 \
    --endpoint-url=http://localhost:4566
