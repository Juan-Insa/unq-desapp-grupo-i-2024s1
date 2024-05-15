import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	war
	jacoco
	id("org.sonarqube") version "4.4.1.3373"
	id("org.springframework.boot") version "3.2.3"
	id("io.spring.dependency-management") version "1.1.4"
	kotlin("jvm") version "1.7.10"
	kotlin("plugin.spring") version "1.8.20"
	kotlin("plugin.jpa") version "1.7.10"

}
jacoco {
	toolVersion = "0.8.11"

}

tasks.jacocoTestReport {
	reports {
		xml.required = true
	}
}

sonar {
	properties {
		property("sonar.projectKey", "Juan-Insa_unq-desapp-grupo-i-2024s1")
		property("sonar.organization", "juan-insa-124708")
		property("sonar.host.url", "https://sonarcloud.io")
	}
}
group = "ar.edu.unq.desapp.grupoI"
version = "0.0.1-SNAPSHOT"

java {
	sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	//implementation("org.springframework.boot:spring-boot-starter-security")
	//implementation("org.hibernate:hibernate-core:5.6.4.Final")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	runtimeOnly("com.mysql:mysql-connector-j")
	implementation("mysql:mysql-connector-java:8.0.33")
	developmentOnly("org.springframework.boot:spring-boot-devtools")
	runtimeOnly("com.h2database:h2")
	providedRuntime("org.springframework.boot:spring-boot-starter-tomcat")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	//testImplementation("org.springframework.security:spring-security-test")
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.2.0")

}

tasks.withType<KotlinCompile> {
	kotlinOptions.jvmTarget = "17"
}

tasks.withType<Test> {
	useJUnitPlatform()
}

