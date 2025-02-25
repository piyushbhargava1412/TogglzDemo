package spring.togglz.toggle.config;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.togglz.core.Feature;
import org.togglz.core.manager.EnumBasedFeatureProvider;
import org.togglz.core.manager.FeatureManager;
import org.togglz.core.manager.FeatureManagerBuilder;
import org.togglz.core.repository.FeatureState;
import org.togglz.core.repository.StateRepository;
import org.togglz.core.spi.FeatureProvider;
import org.togglz.dynamodb.DynamoDBStateRepository;
import spring.togglz.toggle.repository.FeatureToggles;

@Configuration
public class TogglzConfig {

  @Value("${aws.dynamoDb.feature-table-name}")
  private String featureTableName;

  @Bean
  public FeatureProvider featureProvider() {
    return new EnumBasedFeatureProvider(FeatureToggles.class);
  }

  @Bean
  public StateRepository dynamoDBStateRepository(AmazonDynamoDB amazonDynamoDB) {
    return new DynamoDBStateRepository.DynamoDBStateRepositoryBuilder(amazonDynamoDB)
        .withStateStoredInTable(featureTableName)
        .build();
  }

  @Bean
  public FeatureManager featureManager(
      StateRepository stateRepository, FeatureProvider featureProvider) {
    FeatureManager featureManager =
        new FeatureManagerBuilder()
            .featureProvider(featureProvider)
            .stateRepository(stateRepository)
            .build();

    for(Feature feature : featureProvider.getFeatures()) {
      if (stateRepository.getFeatureState(feature) == null)
        stateRepository.setFeatureState(new FeatureState(feature, false));
    }

    return featureManager;
  }
}
