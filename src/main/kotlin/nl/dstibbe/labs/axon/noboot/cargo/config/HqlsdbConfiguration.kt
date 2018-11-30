package nl.dstibbe.labs.axon.noboot.cargo.config


import org.hibernate.cfg.Environment
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseFactoryBean
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter
import org.springframework.transaction.PlatformTransactionManager
import java.util.*
import javax.persistence.EntityManagerFactory
import javax.sql.DataSource

@Configuration
class HsqldbConfiguration {

    @Bean
    fun dataSource() = EmbeddedDatabaseFactoryBean().apply {
        setDatabaseType(EmbeddedDatabaseType.HSQL)
    }

    @Bean
    fun transactionManager(emFactory: EntityManagerFactory): PlatformTransactionManager {
        return JpaTransactionManager(emFactory)
    }


    @Bean
    fun entityManagerFactory(dataSource: DataSource) =
            LocalContainerEntityManagerFactoryBean().apply {
                setDataSource(dataSource)
                setJpaProperties(emProperties())
                setPackagesToScan(
                        "org.axonframework.eventsourcing.eventstore.jpa"
                )
                jpaVendorAdapter = HibernateJpaVendorAdapter()
                persistenceUnitName = "events"
            }


    fun emProperties() = Properties().apply {
        setProperty(Environment.HBM2DDL_AUTO, "update")
        setProperty(Environment.DIALECT, "org.hibernate.dialect.HSQLDialect")
    }


}
