import org.jetbrains.kotlin.gradle.dsl.copyFreeCompilerArgsToArgs
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	java
	id("org.springframework.boot") version "2.7.10"
	id("io.spring.dependency-management") version "1.1.0"
	id("org.jetbrains.kotlin.plugin.jpa") version "1.5.21"
	id("org.jetbrains.kotlin.plugin.allopen") version "1.5.21"
	id("com.github.sherter.google-java-format") version "0.9"



	kotlin("jvm") version "1.8.20"
}

group = "com.example"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.4.0")
	implementation("org.springframework.boot:spring-boot-starter-validation:3.0.4")
	implementation("org.springdoc:springdoc-openapi-ui:1.7.0")
	implementation("org.springframework.boot:spring-boot-starter-security")
	implementation("javax.xml.bind:jaxb-api:2.3.0")
	implementation("io.jsonwebtoken:jjwt:0.9.1")
	implementation("com.auth0:java-jwt:4.3.0")
	compileOnly("org.projectlombok:lombok:1.18.24")
	implementation("org.mapstruct:mapstruct:1.5.3.Final")
	implementation("org.mapstruct:mapstruct-processor:1.5.3.Final")
	implementation("com.fasterxml.jackson.core:jackson-core:2.14.2")
	implementation("com.fasterxml.jackson.core:jackson-databind:2.14.2")
	implementation("org.springframework.data:spring-data-envers:2.7.6")
	implementation("org.springframework.boot:spring-boot-starter-amqp")
	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("org.springframework.boot:spring-boot-starter-mail")
	// https://mvnrepository.com/artifact/com.google.googlejavaformat/google-java-format
	implementation("com.google.googlejavaformat:google-java-format:1.15.0")
	annotationProcessor("org.projectlombok:lombok-mapstruct-binding:0.2.0")
	compileOnly("org.projectlombok:lombok")
	annotationProcessor("org.projectlombok:lombok")
	annotationProcessor("org.mapstruct:mapstruct-processor:1.5.3.Final")
	developmentOnly("org.springframework.boot:spring-boot-devtools")
	runtimeOnly("com.mysql:mysql-connector-j")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	implementation(kotlin("stdlib-jdk8"))
	runtimeOnly("org.jetbrains.kotlin:kotlin-reflect:1.2.41")


}


allOpen {
	annotation("com.my.Annotation")
	// annotations("com.another.Annotation", "com.third.Annotation")
}

tasks.withType<Test> {
	useJUnitPlatform()
}
val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions {
	jvmTarget = "1.8"
}

val compileTestKotlin: KotlinCompile by tasks
compileTestKotlin.kotlinOptions {
	jvmTarget = "1.8"
}

