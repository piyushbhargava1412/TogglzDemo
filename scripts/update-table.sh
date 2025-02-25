#!/bin/bash

# Check if the correct number of arguments are provided
if [ "$#" -ne 2 ]; then
    echo "Usage: $0 <FEATURE_NAME> <true|false>"
    exit 1
fi

FEATURE_NAME=$1
FEATURE_STATE=$2

# Validate input (ensure FEATURE_STATE is either 'true' or 'false')
if [[ "$FEATURE_STATE" != "true" && "$FEATURE_STATE" != "false" ]]; then
    echo "Error: FEATURE_STATE must be either 'true' or 'false'."
    exit 1
fi

# AWS CLI command to update the feature toggle inside featureState.enabled
aws dynamodb update-item \
    --table-name feature_toggles \
    --key "{\"featureName\": {\"S\": \"$FEATURE_NAME\"}}" \
    --update-expression "SET featureState.enabled = :state" \
    --expression-attribute-values "{\":state\": {\"BOOL\": $FEATURE_STATE}}" \
    --region us-east-1 \
    --endpoint-url=http://localhost:4566

# Check if the command was successful
if [ $? -eq 0 ]; then
    echo "Feature '$FEATURE_NAME' updated successfully to $FEATURE_STATE."
else
    echo "Error updating feature '$FEATURE_NAME'."
    exit 1
fi
