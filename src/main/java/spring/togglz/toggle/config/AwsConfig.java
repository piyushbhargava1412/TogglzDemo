package spring.togglz.toggle.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AwsConfig {

  @Bean
  public AWSStaticCredentialsProvider awsCredentialsProvider() {
    // Use dummy credentials for LocalStack
    AWSCredentials credentials = new BasicAWSCredentials("test", "test");
    return new AWSStaticCredentialsProvider(credentials);
  }

  @Bean
  public AmazonDynamoDB dynamoDB(AWSCredentialsProvider credentialsProvider) {
    return AmazonDynamoDBClientBuilder.standard()
        .withCredentials(credentialsProvider)
        .withEndpointConfiguration(
            new AwsClientBuilder.EndpointConfiguration("http://localhost:4566", "us-east-1"))
        .build();
  }
}
