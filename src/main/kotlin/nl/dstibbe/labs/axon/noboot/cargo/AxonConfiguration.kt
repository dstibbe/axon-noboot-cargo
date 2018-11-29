package nl.dstibbe.labs.axon.noboot.cargo

import nl.dstibbe.labs.axon.noboot.cargo.aggregates.Cargo
import org.axonframework.commandhandling.CommandBus
import org.axonframework.commandhandling.SimpleCommandBus
import org.axonframework.commandhandling.gateway.DefaultCommandGateway
import org.axonframework.config.DefaultConfigurer
import org.axonframework.eventsourcing.CachingEventSourcingRepository
import org.axonframework.eventsourcing.eventstore.EmbeddedEventStore
import org.axonframework.eventsourcing.eventstore.EventStorageEngine
import org.axonframework.eventsourcing.eventstore.EventStore
import org.axonframework.eventsourcing.eventstore.inmemory.InMemoryEventStorageEngine
import org.axonframework.modelling.command.Repository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration

@Configuration
@ComponentScan
class AxonConfiguration {
    companion object:Logger()

    init{
        log.info("Loading Axon Configuration")
    }

    @Bean
    fun commandBus() =
            SimpleCommandBus.builder()
                    .build()


    @Bean
    fun commandGateway(commandBus: CommandBus) =
            DefaultCommandGateway.builder()
                    .commandBus(commandBus)
                    .build()


    @Bean
    fun eventStorageEngine() = InMemoryEventStorageEngine()


    @Bean
    fun eventStore(eventStorageEngine: EventStorageEngine) = EmbeddedEventStore.builder()
            .storageEngine(eventStorageEngine)
            .build()

    @Bean
    fun aggregateRepo(eventStore: EventStore): Repository<Cargo> =
            CachingEventSourcingRepository.builder(Cargo::class.java)
                    .eventStore(eventStore)
                    .build()


    @Bean
    fun axonConfig(commandBus: CommandBus, eventStore: EventStore): org.axonframework.config.Configuration {

        val configurer = DefaultConfigurer.defaultConfiguration()
        configurer.configureAggregate(Cargo::class.java)

        configurer.configureCommandBus { c -> commandBus }
        configurer.configureEventStore { c -> eventStore }
        val configuration = configurer
                .buildConfiguration()

        configuration.start()
        return configuration
    }
}
