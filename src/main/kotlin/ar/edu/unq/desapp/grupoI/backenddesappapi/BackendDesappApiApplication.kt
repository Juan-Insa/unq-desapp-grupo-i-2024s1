package ar.edu.unq.desapp.grupoI.backenddesappapi

import ar.edu.unq.desapp.grupoI.backenddesappapi.utils.DataService
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.stereotype.Component
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
	@Component
	class DataLoader(private val dataService: DataService) : ApplicationRunner {

		override fun run(args: ApplicationArguments?) {
			dataService.createTestData()
		}
	}
}