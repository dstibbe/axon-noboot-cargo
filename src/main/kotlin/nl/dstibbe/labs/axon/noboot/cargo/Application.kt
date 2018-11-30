package nl.dstibbe.labs.axon.noboot.cargo

import nl.dstibbe.labs.axon.noboot.cargo.config.AppConfig
import org.springframework.beans.factory.NoSuchBeanDefinitionException
import org.springframework.context.annotation.AnnotationConfigApplicationContext


fun main() {
    println("Booting ...")
    val context = AnnotationConfigApplicationContext(AppConfig::class.java)

    try {
        val runner= context.getBean("consoleRunner") as ConsoleRunner
        runner.run()
    }catch(e:NoSuchBeanDefinitionException){
        println("Failed to load console runner")
    }
}
