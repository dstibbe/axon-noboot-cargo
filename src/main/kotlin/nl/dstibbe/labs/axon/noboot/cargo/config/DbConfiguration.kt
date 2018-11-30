package nl.dstibbe.labs.axon.noboot.cargo.config


import org.apache.commons.dbcp2.BasicDataSource
import org.hibernate.cfg.Environment
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter
import org.springframework.transaction.PlatformTransactionManager
import java.util.*
import javax.persistence.EntityManagerFactory
import javax.sql.DataSource

@Configuration
class DbConfiguration {

    private fun emProperties() = Properties().apply {
        setProperty(Environment.HBM2DDL_AUTO, "drop-and-create")
//        setProperty(Environment.HBM2DDL_AUTO, "update")
        setProperty(Environment.DIALECT, "org.hibernate.dialect.H2Dialect")
    }

    @Bean
    fun h2DataSource() = BasicDataSource().apply {
        username = "sa"
        password = ""
        url = "jdbc:h2:file:./target/db/eventsdb;FILE_LOCK=NO"
        driverClassName = "org.h2.Driver"
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


}
