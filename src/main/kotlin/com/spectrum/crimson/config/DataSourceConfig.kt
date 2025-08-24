package com.spectrum.crimson.config

import com.spectrum.crimson.config.persistence.TransactionManager
import com.zaxxer.hikari.HikariDataSource
import liquibase.integration.spring.SpringLiquibase
import org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties
import org.springframework.boot.autoconfigure.orm.jpa.HibernateProperties
import org.springframework.boot.autoconfigure.orm.jpa.HibernateSettings
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.support.TransactionTemplate
import javax.sql.DataSource


@Configuration
@EnableJpaRepositories(
    basePackages = ["com.spectrum.crimson.domain.repository"],
    transactionManagerRef = "transactionManager",
    entityManagerFactoryRef = "entityManagerFactory"
)
class DataSourceConfig {

    @Bean
    @ConfigurationProperties("spring.datasource")
    fun dataSourceProperties(): DataSourceProperties {
        return DataSourceProperties()
    }

    @Bean
    @ConfigurationProperties("spring.datasource.hikari")
    fun dataSource(): DataSource {
        return dataSourceProperties()
            .initializeDataSourceBuilder()
            .type(HikariDataSource::class.java)
            .build()
            .apply {
                poolName = "crimsonMain"
                isAutoCommit = false
                maxLifetime = 60000
                maximumPoolSize = 20
            }
    }

    @Bean
    @Primary
    @ConfigurationProperties("spring.jpa")
    fun jpaProperties(): JpaProperties {
        return JpaProperties()
    }

    @Bean
    fun entityManagerFactory(): LocalContainerEntityManagerFactoryBean {
        val factory = LocalContainerEntityManagerFactoryBean()
        factory.jpaVendorAdapter = HibernateJpaVendorAdapter()
        factory.dataSource = dataSource()
        factory.setPackagesToScan(
            "com.crimson.domain.entity",
        )
        factory.jpaPropertyMap.putAll(
            HibernateProperties()
                .determineHibernateProperties(
                    jpaProperties().properties,
                    HibernateSettings(),
                ),
        )
        factory.jpaPropertyMap.putAll(
            mapOf(
                "hibernate.physical_naming_strategy" to PhysicalNamingStrategyStandardImpl.INSTANCE,
                "hibernate.dialect" to "org.hibernate.dialect.MySQLDialect",
                "hibernate.generate_statistics" to true,
                "hibernate.connection.provider_disables_autocommit" to true,
                "hibernate.query.in_clause_parameter_padding" to true,
                "hibernate.hbm2ddl.auto" to "none"
            ),
        )
        return factory
    }

    @Bean
    fun transactionManager(): PlatformTransactionManager {
        return TransactionManager(JpaTransactionManager(entityManagerFactory().`object`!!))
    }

    @Bean
    fun transactionTemplate(): TransactionTemplate {
        return TransactionTemplate(transactionManager())
    }

    @Bean
    @ConfigurationProperties("liquibase")
    fun liquibase(): SpringLiquibase {
        val liquibase = SpringLiquibase()
        liquibase.changeLog = "classpath:/db/changelog/db.changelog-master.yml"
        liquibase.dataSource = dataSource()
        liquibase.setShouldRun(false)
        return liquibase
    }
}