package nl.dstibbe.labs.axon.noboot.cargo


import org.hibernate.cfg.Environment
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter
import java.util.*
import javax.sql.DataSource

@Configuration
class HsqldbConfiguration {

    //
    //  @Override
    //  public BasicDataSource createDataSource() {
    //    BasicDataSource dataSource = new BasicDataSource();
    //    dataSource.setUsername(dbProperties.getDbUserName());
    //    dataSource.setPassword(dbProperties.getDbPassword());
    //    dataSource.setUrl(dbProperties.getDbUrl());
    //    dataSource.setDriverClassName(dbProperties.getDbDriver());
    //    dataSource.setInitialSize(3);
    //    dataSource.setMaxTotal(50);
    //    dataSource.setMaxTotal(dbProperties.getDbPoolMaxActive());
    //
    //    log.info("Initializing db");
    //    dbProperties.getSqlScripts().ifPresent(scripts ->
    //    {
    //      ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator();
    //      for (String script : scripts) {
    //        log.info("Executing script: " + script);
    //        Resource resource = new ClassPathResource(script);
    //        databasePopulator.addScript(resource);
    //      }
    //      databasePopulator.execute(dataSource);
    //    });
    //
    //    return dataSource;
    //  }
    //

    @Bean
    fun createEntityManagerFactory(
            //TODO: add dataSource
//            dataSource: DataSource
    ) =
            LocalContainerEntityManagerFactoryBean().apply {
//                setDataSource(dataSource)
                setJpaProperties(emProperties())
//                setPackagesToScan(packagesToScan)
                jpaVendorAdapter = HibernateJpaVendorAdapter()
                persistenceUnitName = "events"
            }


    fun emProperties() = Properties().apply {
        setProperty(Environment.HBM2DDL_AUTO, "update")
        setProperty(Environment.DIALECT, "org.hibernate.dialect.HSQLDialect")
    }


}
