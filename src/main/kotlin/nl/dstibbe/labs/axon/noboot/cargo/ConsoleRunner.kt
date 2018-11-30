package nl.dstibbe.labs.axon.noboot.cargo

import nl.dstibbe.labs.axon.noboot.cargo.commands.OnboardCargo
import nl.dstibbe.labs.axon.noboot.cargo.commands.SendCargo
import nl.dstibbe.labs.axon.noboot.cargo.ids.CargoId
import nl.dstibbe.labs.axon.noboot.cargo.ids.CarrierId
import nl.dstibbe.labs.axon.noboot.cargo.ids.Location
import org.axonframework.commandhandling.gateway.CommandGateway
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import javax.transaction.Transactional

@Component
class ConsoleRunner {
    companion object : Logger()

    @Autowired
    lateinit var commandGateway: CommandGateway

    @Transactional
    fun run() {
        try {
            runAxon()
        } catch (e: Exception) {
            log.error("something went wrong", e)
        }
    }

    private fun runAxon() {
        val targetId = CargoId("Hello World")


        log.info("[SEND COMMAND] SendCargo")
        commandGateway.sendAndWait<Unit>(SendCargo(targetId))

        log.info("[SEND COMMAND] OnboardCargo")
        commandGateway.sendAndWait<Unit>(OnboardCargo(targetId, CarrierId("Schuitje"), Location.NEW_YORK))
    }
}
