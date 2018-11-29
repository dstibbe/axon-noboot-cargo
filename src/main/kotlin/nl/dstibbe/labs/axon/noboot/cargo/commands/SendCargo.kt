package nl.dstibbe.labs.axon.noboot.cargo.commands

import nl.dstibbe.labs.axon.noboot.cargo.ids.CargoId
import org.axonframework.modelling.command.TargetAggregateIdentifier

data class SendCargo(
    @TargetAggregateIdentifier
    val id: CargoId
)
