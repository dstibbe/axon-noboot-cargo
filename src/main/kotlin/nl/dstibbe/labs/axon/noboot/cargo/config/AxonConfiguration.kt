package nl.dstibbe.labs.axon.noboot.cargo.config

import nl.dstibbe.labs.axon.noboot.cargo.Logger
import nl.dstibbe.labs.axon.noboot.cargo.aggregates.Cargo
import nl.dstibbe.labs.axon.noboot.cargo.projector.Projector
import org.axonframework.commandhandling.CommandBus
import org.axonframework.commandhandling.SimpleCommandBus
import org.axonframework.commandhandling.gateway.DefaultCommandGateway
import org.axonframework.config.DefaultConfigurer
import org.axonframework.eventsourcing.eventstore.EmbeddedEventStore
import org.axonframework.eventsourcing.eventstore.EventStorageEngine
import org.axonframework.eventsourcing.eventstore.EventStore
import org.axonframework.eventsourcing.eventstore.jpa.JpaEventStorageEngine
import org.axonframework.spring.messaging.unitofwork.SpringTransactionManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.transaction.PlatformTransactionManager

@Configuration
@ComponentScan
class AxonConfiguration {
    companion object : Logger()

    init {
        log.info("Loading Axon Configuration")
    }

    @Bean
    fun axonTxManager(transactionManager: PlatformTransactionManager) =
            SpringTransactionManager(transactionManager)


    @Bean
    fun commandBus(txManager: SpringTransactionManager) =
            SimpleCommandBus.builder()
                    .transactionManager(txManager)
                    .build()


    @Bean
    fun commandGateway(commandBus: CommandBus) =
            DefaultCommandGateway.builder()
                    .commandBus(commandBus)
                    .build()


    @Bean
    fun eventStorageEngine(
            entityManagerProvider: JpaEntityManagerProvider,
            txManager: SpringTransactionManager
    ) = JpaEventStorageEngine.builder()
            .transactionManager(txManager)
            .entityManagerProvider(entityManagerProvider)
            .build()


    @Bean
    fun eventStore(eventStorageEngine: EventStorageEngine) = EmbeddedEventStore.builder()
            .storageEngine(eventStorageEngine)
            .build()


    @Bean
    fun axonConfig(commandBus: CommandBus, eventStore: EventStore, projector: Projector): org.axonframework.config.Configuration {

        val configurer = DefaultConfigurer.defaultConfiguration()
        configurer.configureAggregate(Cargo::class.java)

        configurer.configureCommandBus { commandBus }
        configurer.configureEventStore { eventStore }

        configurer.eventProcessing { ec ->
            // Enable this to change the event handler from tracking to subscribing
//            ec.usingSubscribingEventProcessors()
            ec.registerEventHandler { projector }

        }

        val configuration = configurer
                .buildConfiguration()

        configuration.start()
        return configuration
    }
}
