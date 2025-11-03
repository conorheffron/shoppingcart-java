plugins {
    `java-library`
    `maven-publish`
    java
    id("io.freefair.lombok") version "9.0.0" // Lombok plugin for Gradle
    id("com.adarshr.test-logger") version "4.0.0"
    id("jacoco")
    id("org.sonarqube") version "6.3.1.5724" // Use the latest version
}

sonar {
    properties {
        property("sonar.projectKey", "conorheffron_shoppingcart-java")
        property("sonar.organization", "conorheffron")
    }
}

repositories {
    mavenLocal()
    maven {
        url = uri("https://repo.maven.apache.org/maven2/")
    }

    maven {
        url = uri("https://maven.pkg.github.com/conorheffron/*")
    }
}

tasks.withType<Test> {
    // Discover and execute JUnit Platform-based (JUnit 5/JUnit Jupiter) tests
    // Note that JUnit 5 has the ability to execute JUnit 4 tests as well
    useJUnitPlatform()
}

dependencies {
    annotationProcessor(libs.org.projectlombok.lombok) // Annotation processor

    compileOnly(libs.org.projectlombok.lombok) // Lombok dependency
    
    api(libs.org.springframework.boot.spring.boot.starter)
    api(libs.org.springframework.boot.spring.boot.starter.web)
    api(libs.net.sourceforge.jwebunit.jwebunit.core)
    api(libs.net.sourceforge.jwebunit.jwebunit)
    api(libs.javax.xml.bind.jaxb.api)
    api(libs.com.sun.xml.bind.jaxb.core)
    api(libs.com.sun.xml.bind.jaxb.impl)
    api(libs.org.springframework.boot.spring.boot.starter.security)
    api(libs.io.jsonwebtoken.jjwt)
    api(libs.org.springframework.boot.spring.boot.starter.validation)
    api(libs.commons.httpclient.commons.httpclient)
    api(libs.org.apache.commons.commons.lang3)
    api(libs.org.springframework.boot.spring.boot.starter.quartz)
    api(libs.org.projectlombok.lombok)
    api(libs.org.apache.commons.commons.csv)
    api(libs.org.springframework.boot.spring.boot.starter.data.jpa)
    api(libs.org.apache.httpcomponents.httpclient)
    api(libs.org.springframework.boot.spring.boot.starter.thymeleaf)
    api(libs.com.google.code.gson.gson)
    api(libs.org.springdoc.springdoc.openapi.starter.webmvc.ui)
    api(libs.org.webjars.jquery)
    api(libs.org.webjars.bootstrap)
    api(libs.org.webjars.webjars.locator.core)
    api(libs.org.apache.opennlp.opennlp.tools)
    api(libs.org.apache.poi.poi)
    api(libs.org.apache.logging.log4j.log4j.to.slf4j)
    api(libs.org.apache.logging.log4j.log4j.api)
    api(libs.org.apache.logging.log4j.log4j.core)
    api(libs.org.apache.poi.poi.ooxml)
    api(libs.org.apache.poi.poi.scratchpad)
    api(libs.com.opencsv.opencsv)
    api(libs.org.jxls.jxls.jexcel)
    api(libs.org.dhatim.fastexcel.reader)
    api(libs.org.dhatim.fastexcel)

    runtimeOnly(libs.org.springframework.boot.spring.boot.devtools)
    runtimeOnly(libs.mysql.mysql.connector.java)

    testAnnotationProcessor(libs.org.projectlombok.lombok)

    testCompileOnly(libs.org.projectlombok.lombok)

    testRuntimeOnly(libs.junit.platform.launcher)
    testRuntimeOnly(libs.junit.platform.engine)
    testRuntimeOnly(libs.junit.platform.launcher)

    testImplementation(libs.net.sourceforge.jwebunit.jwebunit.htmlunit.plugin)
    testImplementation(libs.org.springframework.boot.spring.boot.starter.test)
    testImplementation(libs.org.springframework.security.spring.security.test)
    testImplementation(libs.org.junit.jupiter)// JUnit 5 API for writing tests
}

group = "com.siriusxm.example.cart"
version = "1.1.0-RELEASE"
description = "shoppingcart"
java.sourceCompatibility = JavaVersion.VERSION_21

publishing {
    publications.create<MavenPublication>("maven") {
        from(components["java"])
    }
}

tasks.withType<JavaCompile>() {
    options.encoding = "UTF-8"
}

tasks.withType<Javadoc>() {
    options.encoding = "UTF-8"
}

tasks {
    compileJava {
        options.compilerArgs.add("-parameters") // Optional: Enables parameter names in reflection
    }
}

tasks.withType<JacocoReport> {
    dependsOn(tasks.withType<Test>()) // Ensure tests run before generating the report

    reports {
        xml.required.set(true) // Enable XML report generation
    }
}
