package nl.dstibbe.labs.axon.noboot.cargo.commands

import nl.dstibbe.labs.axon.noboot.cargo.ids.CargoId

interface CargoCommand {
    val id: CargoId
}
