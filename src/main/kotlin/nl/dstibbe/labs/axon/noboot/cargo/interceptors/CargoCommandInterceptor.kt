package nl.dstibbe.labs.axon.noboot.cargo.interceptors

import nl.dstibbe.labs.axon.noboot.cargo.Logger
import org.axonframework.commandhandling.CommandMessage
import org.axonframework.messaging.MessageDispatchInterceptor
import java.util.function.BiFunction

class CargoCommandInterceptor : MessageDispatchInterceptor<CommandMessage<*>> {
    companion object : Logger()


    override fun handle(messages: MutableList<out CommandMessage<*>>?): BiFunction<Int, CommandMessage<*>, CommandMessage<*>> {
        return BiFunction{ index, command ->
            log.info("Intercepting command {}.", command)

            log.info("Nothing to see here, move along.")
            command
        }


    }
}
