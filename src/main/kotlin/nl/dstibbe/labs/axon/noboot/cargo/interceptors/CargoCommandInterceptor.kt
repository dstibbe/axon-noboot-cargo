package nl.dstibbe.labs.axon.noboot.cargo.interceptors

import nl.dstibbe.labs.axon.noboot.cargo.Logger
import nl.dstibbe.labs.axon.noboot.cargo.aggregates.Cargo
import nl.dstibbe.labs.axon.noboot.cargo.commands.CargoCommand
import nl.dstibbe.labs.axon.noboot.cargo.ids.CargoId
import org.axonframework.commandhandling.CommandMessage
import org.axonframework.messaging.MessageDispatchInterceptor
import org.axonframework.messaging.unitofwork.DefaultUnitOfWork
import org.axonframework.modelling.command.Aggregate
import org.axonframework.modelling.command.AggregateNotFoundException
import org.axonframework.modelling.command.Repository
import java.util.function.BiFunction

class CargoCommandInterceptor(private val aggregateRepo: Repository<Cargo>) : MessageDispatchInterceptor<CommandMessage<*>> {
    companion object : Logger()


    override fun handle(messages: MutableList<out CommandMessage<*>>?): BiFunction<Int, CommandMessage<*>, CommandMessage<*>> =
            BiFunction { index, command ->
                log.info("Intercepting command {}.", command)

                aggregateExists(command)

                command
            }

    fun aggregateExists(command: CommandMessage<*>) {
        DefaultUnitOfWork.startAndGet(command)
                .execute {
                    val payload = command.payload as? CargoCommand

                    payload?.run {
                        loadAggregate(id)?.apply {
                            log.info("aggregate exists")
                        } ?: log.info("aggregate does not yet exist")
                    }
                }
    }

    fun loadAggregate(id: CargoId): Aggregate<Cargo>? {
        try {
            return aggregateRepo.load(id.value)
        } catch (e: AggregateNotFoundException) {
            log.info("Aggregate with id ${id} not found")
            //agregate not found
        } catch (e: Exception) {
            log.error("Something went wrong", e)
        }
        return null
    }
}
