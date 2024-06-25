package ar.edu.unq.desapp.grupoI.backenddesappapi

import com.tngtech.archunit.core.domain.JavaClasses
import com.tngtech.archunit.core.importer.ClassFileImporter
import com.tngtech.archunit.core.importer.ImportOption
import com.tngtech.archunit.lang.ArchRule
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses
import com.tngtech.archunit.library.Architectures.layeredArchitecture
import org.junit.jupiter.api.Test


class ArchitectureTests {
    private val importedClasses: JavaClasses = ClassFileImporter()
        .withImportOption(ImportOption.DoNotIncludeTests())
        .importPackages("ar.edu.unq.desapp.grupoI.backenddesappapi")
    @Test
    fun `Check no dependency from controllers to persistence`() {
        val rule: ArchRule = noClasses()
            .that().resideInAPackage("..webservice.controllers..")
            .should().dependOnClassesThat()
            .resideInAPackage("..persistence..")

        rule.check(importedClasses)
    }
    @Test
    fun `Check no dependency from model to controllers`() {
        val rule: ArchRule = noClasses()
            .that().resideInAPackage("..model..")
            .should().dependOnClassesThat()
            .resideInAPackage("..controllers..")

        rule.check(importedClasses)
    }
    @Test
    fun `Check no dependency from service to controllers`() {
        val rule: ArchRule = noClasses()
            .that().resideInAPackage("..service..")
            .should().dependOnClassesThat()
            .resideInAPackage("..controllers")

        rule.check(importedClasses)
    }
    @Test
    fun `Check no dependency from persistence to service`() {
        val rule: ArchRule = noClasses()
            .that().resideInAPackage("..persistence..")
            .should().dependOnClassesThat()
            .resideInAPackage("..service..")

        rule.check(importedClasses)
    }

    @Test
    fun `Check all classes in controllers has name ending with controller`() {
        val rule: ArchRule = classes()
            .that().resideInAPackage("..webservice.controllers")
            .should().haveSimpleNameEndingWith("Controller")
        rule.check(importedClasses)
    }
    @Test
    fun layeredArchitectureShouldBeRespected() {
        layeredArchitecture()
            .consideringAllDependencies()
            .layer("Controller").definedBy("..webservice..")
            .layer("Service").definedBy("..service..")
            .layer("Persistence").definedBy("..persistence..")
            .whereLayer("Controller").mayNotBeAccessedByAnyLayer()
            .whereLayer("Service").mayOnlyBeAccessedByLayers("Controller")
            .whereLayer("Persistence").mayOnlyBeAccessedByLayers("Service")
    }
}