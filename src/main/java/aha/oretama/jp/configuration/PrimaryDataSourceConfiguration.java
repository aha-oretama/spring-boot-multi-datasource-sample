package aha.oretama.jp.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.flyway.FlywayDataSource;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
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
    basePackages = "aha.oretama.jp.repository.primary",
    entityManagerFactoryRef = "primaryEntityManager",
    transactionManagerRef = "primaryTransactionManager"
)
public class PrimaryDataSourceConfiguration {

  @Bean
  @Primary
  @ConfigurationProperties(prefix = "spring.datasource.primary")
  public DataSourceProperties primaryProperties() {
    return new DataSourceProperties();
  }

  @Bean
  @Primary
  @Autowired
  public DataSource primaryDataSource(@Qualifier("primaryProperties")
      DataSourceProperties properties) {
    return createDataSource(properties);
  }

  @Bean
  @Primary
  @Autowired
  public LocalContainerEntityManagerFactoryBean primaryEntityManager(@Qualifier("primaryDataSource") DataSource dataSource) {
    LocalContainerEntityManagerFactoryBean entityManager = new LocalContainerEntityManagerFactoryBean();
    entityManager.setDataSource(dataSource);
    entityManager.setPackagesToScan(new String[] {"aha.oretama.jp.entity.primary"});

    HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
    entityManager.setJpaVendorAdapter(vendorAdapter);

    return entityManager;
  }

  @Bean
  @Primary
  @Autowired
  public PlatformTransactionManager primaryTransactionManager(@Qualifier("primaryEntityManager") LocalContainerEntityManagerFactoryBean primaryEntityManager) {
    JpaTransactionManager transactionManager = new JpaTransactionManager();
    transactionManager.setEntityManagerFactory(primaryEntityManager.getObject());
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
