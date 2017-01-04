package aha.oretama.jp.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.flyway.FlywayDataSource;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
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
    return createDataSource(properties);
  }

  @Bean
  @Autowired
  public LocalContainerEntityManagerFactoryBean secondaryEntityManager(@Qualifier("secondaryDataSource") DataSource dataSource) {
    LocalContainerEntityManagerFactoryBean entityManager = new LocalContainerEntityManagerFactoryBean();
    entityManager.setDataSource(dataSource);
    entityManager.setPackagesToScan(new String[] {"aha.oretama.jp.entity.secondary"});

    HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
    entityManager.setJpaVendorAdapter(vendorAdapter);

    return entityManager;
  }

  @Bean
  @Autowired
  public PlatformTransactionManager secondaryTransactionManager(@Qualifier("secondaryEntityManager") LocalContainerEntityManagerFactoryBean secondaryEntityManager) {
    JpaTransactionManager transactionManager = new JpaTransactionManager();
    transactionManager.setEntityManagerFactory(secondaryEntityManager.getObject());
    return transactionManager;
  }

  private DataSource createDataSource(DataSourceProperties properties){
    return DataSourceBuilder
        .create()
        .driverClassName(properties.getDriverClassName())
        .url(properties.getUrl())
        .username(properties.getUsername())
        .password(properties.getPassword())
        .build();

  }

}
