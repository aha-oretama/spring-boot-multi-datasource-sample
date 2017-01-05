package aha.oretama.jp.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.flyway.FlywayDataSource;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

/**
 * @author aha-oretama
 */
@Configuration
@EnableJpaRepositories(
    basePackages = "aha.oretama.jp.repository.secondary",
    entityManagerFactoryRef = "secondaryEntityManager",
    transactionManagerRef = "secondaryTransactionManager"
)
public class SecondaryDataSourceConfiguration {

  @Bean
  @ConfigurationProperties(prefix = "spring.datasource.secondary")
  public DataSourceProperties secondaryProperties() {
    return new DataSourceProperties();
  }

  @Bean
  @Autowired
  @FlywayDataSource
  public DataSource secondaryDataSource(@Qualifier("secondaryProperties")
      DataSourceProperties properties) {
    return properties.initializeDataSourceBuilder().build();
  }

  @Bean
  @Autowired
  public LocalContainerEntityManagerFactoryBean secondaryEntityManager(EntityManagerFactoryBuilder builder,@Qualifier("secondaryDataSource") DataSource dataSource){
    return builder.dataSource(dataSource)
        .packages("aha.oretama.jp.entity.secondary")
        .persistenceUnit("secondary")
        .build();
  }

  @Bean
  @Autowired
  public JpaTransactionManager secondaryTransactionManager(@Qualifier("secondaryEntityManager") LocalContainerEntityManagerFactoryBean secondaryEntityManager) {
    JpaTransactionManager transactionManager = new JpaTransactionManager();
    transactionManager.setEntityManagerFactory(secondaryEntityManager.getObject());
    return transactionManager;
  }

}
