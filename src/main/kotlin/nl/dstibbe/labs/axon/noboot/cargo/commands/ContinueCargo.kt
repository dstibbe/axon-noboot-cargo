package nl.dstibbe.labs.axon.noboot.cargo.commands

import nl.dstibbe.labs.axon.noboot.cargo.ids.CargoId
import org.axonframework.modelling.command.TargetAggregateIdentifier

data class ContinueCargo(
        @TargetAggregateIdentifier
        override val id: CargoId
): CargoCommand
