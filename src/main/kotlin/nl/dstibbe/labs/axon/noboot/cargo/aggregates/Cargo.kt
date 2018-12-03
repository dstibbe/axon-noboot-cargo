package nl.dstibbe.labs.axon.noboot.cargo.aggregates

import nl.dstibbe.labs.axon.noboot.cargo.Logger
import nl.dstibbe.labs.axon.noboot.cargo.commands.ContinueCargo
import nl.dstibbe.labs.axon.noboot.cargo.commands.OnboardCargo
import nl.dstibbe.labs.axon.noboot.cargo.commands.SendCargo
import nl.dstibbe.labs.axon.noboot.cargo.events.CargoCreated
import nl.dstibbe.labs.axon.noboot.cargo.events.CargoOnboarded
import nl.dstibbe.labs.axon.noboot.cargo.ids.CargoId
import org.axonframework.commandhandling.CommandHandler
import org.axonframework.eventhandling.EventHandler
import org.axonframework.modelling.command.AggregateIdentifier
import org.axonframework.modelling.command.AggregateLifecycle
import org.axonframework.spring.stereotype.Aggregate

@Aggregate
class Cargo() {
    companion object : Logger()

    @AggregateIdentifier
    lateinit var id: CargoId

    var onboarded = false

    @CommandHandler
    constructor(command: SendCargo) : this() {
        log.info("[HANDLE COMMAND] SendCargo")
        AggregateLifecycle.apply(CargoCreated(command.id))
    }

    @CommandHandler
    fun continueCargo(command: ContinueCargo) {
        log.info("[HANDLE COMMAND] ContinueCargo --> not doing anything")
    }

    @CommandHandler
    fun onboard(command: OnboardCargo) {
        log.info("[HANDLE COMMAND] OnboardCargo")
        if (onboarded) {
            throw IllegalStateException("Cannot onboard, since it is already onboarded")
        }
        AggregateLifecycle.apply(CargoOnboarded(command.id))
    }


    @EventHandler
    fun createCargo(event: CargoCreated) {
        log.info("[APPLY EVENT] CargoCreated")
        this.id = event.id
    }


    @EventHandler
    fun createCargo(event: CargoOnboarded) {
        log.info("[APPLY EVENT] CargoOnboarded")
        this.onboarded = true
    }


}
