package nl.dstibbe.labs.axon.noboot.cargo.commands

import nl.dstibbe.labs.axon.noboot.cargo.ids.CargoId
import nl.dstibbe.labs.axon.noboot.cargo.ids.CarrierId
import nl.dstibbe.labs.axon.noboot.cargo.ids.Location
import org.axonframework.modelling.command.TargetAggregateIdentifier

data class OnboardCargo(
        @TargetAggregateIdentifier
        override val id: CargoId,
        val carrier: CarrierId,
        val location: Location
): CargoCommand
