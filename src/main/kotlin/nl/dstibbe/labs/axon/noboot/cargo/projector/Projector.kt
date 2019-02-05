package nl.dstibbe.labs.axon.noboot.cargo.projector

import nl.dstibbe.labs.axon.noboot.cargo.Logger
import nl.dstibbe.labs.axon.noboot.cargo.events.CargoCreated
import nl.dstibbe.labs.axon.noboot.cargo.events.CargoOnboarded
import org.axonframework.eventhandling.EventHandler
import org.springframework.stereotype.Component
import javax.transaction.Transactional

@Component
@Transactional
class Projector {

    companion object : Logger()

    @EventHandler
    fun handleCargoCreated(event: CargoCreated) {
        log.info("Projecting cargoCreated")
    }


    @EventHandler
    fun handleCargoOnboarded(event: CargoOnboarded) {
        log.info("Projecting cargoOnboarded")
    }
}
