package ar.edu.unq.desapp.grupoI.backenddesappapi

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.web.bind.annotation.RestController

@SpringBootApplication
@RestController
class BackendDesappApiApplication {

	companion object {
		@JvmStatic
		fun main(args: Array<String>) {
			SpringApplication.run(BackendDesappApiApplication::class.java, *args)
		}
	}

}