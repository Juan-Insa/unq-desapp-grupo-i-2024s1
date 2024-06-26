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
		xml.required.set(true)
	}
}

sonar {
	properties {
		property("sonar.projectKey", "Juan-Insa_unq-desapp-grupo-i-2024s1")
		property("sonar.organization", "juan-insa-124708")
		property("sonar.host.url", "https://sonarcloud.io")
		property("sonar.gradle.skipCompile", System.getProperty("sonar.gradle.skipCompile", "false"))
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

val archVersion = "1.3.0"
val jwtVersion  = "0.12.5"
val log4jVersion = "2.23.1"
val mySQLVersion = "8.0.33"
val springDocVersion = "2.2.0"
val springCacheVersion = "3.1.5"
val javaxCacheVersion = "1.1.1"
val ehCacheVersion = "3.10.8"

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-security")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("jakarta.servlet:jakarta.servlet-api")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("mysql:mysql-connector-java:${mySQLVersion}")
	developmentOnly("org.springframework.boot:spring-boot-devtools")
	runtimeOnly("com.h2database:h2")
	providedRuntime("org.springframework.boot:spring-boot-starter-tomcat")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.springframework.security:spring-security-test")
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:${springDocVersion}")
	implementation("org.springframework.boot:spring-boot-starter-cache:${springCacheVersion}")
	implementation("javax.cache:cache-api:${javaxCacheVersion}")
	implementation("org.ehcache:ehcache:${ehCacheVersion}:jakarta")
	testImplementation("com.tngtech.archunit:archunit:${archVersion}")

	// security/jwt
	implementation("org.springframework.boot:spring-boot-starter-oauth2-resource-server")
	implementation("io.jsonwebtoken:jjwt-api:${jwtVersion}")
	runtimeOnly("io.jsonwebtoken:jjwt-impl:${jwtVersion}")
	runtimeOnly("io.jsonwebtoken:jjwt-jackson:${jwtVersion}")

	// log4j
	implementation("org.apache.logging.log4j:log4j-api:${log4jVersion}")
	implementation("org.apache.logging.log4j:log4j-core:${log4jVersion}")
	implementation("org.springframework.boot:spring-boot-starter-aop")
	implementation("org.springframework.boot:spring-boot-starter-logging")

	//prometheus
	implementation("org.springframework.boot:spring-boot-starter-actuator")
	implementation("io.micrometer:micrometer-core")
	implementation("io.micrometer:micrometer-registry-prometheus")

	// AOP
	implementation("org.springframework.boot:spring-boot-starter-aop")

}

tasks.withType<KotlinCompile> {
	kotlinOptions.jvmTarget = "17"
}

tasks.withType<Test> {
	useJUnitPlatform()
}

