plugins {
	java
	id("org.springframework.boot") version "2.7.10"
	id("io.spring.dependency-management") version "1.1.0"
}

group = "guru.springframework"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
	mavenCentral()
}

dependencies {

	annotationProcessor("org.projectlombok:lombok:1.18.20")
	compileOnly("org.projectlombok:lombok:1.18.20")
	testAnnotationProcessor("org.projectlombok:lombok:1.18.20")
	
	implementation("org.springframework.boot:spring-boot-starter-data-mongodb")
	implementation("org.springframework.boot:spring-boot-starter-data-mongodb-reactive")
	implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
	implementation("org.springframework.boot:spring-boot-starter-validation:2.7.6")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.webjars:bootstrap:3.3.7-1")
	implementation("de.flapdoodle.embed:de.flapdoodle.embed.mongo")

	developmentOnly("org.springframework.boot:spring-boot-devtools")
	
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation(group = "cz.jirutka.spring", name = "embedmongo-spring", version = "1.3.1")
	testImplementation("org.hamcrest:hamcrest:2.2")
	testImplementation("io.projectreactor:reactor-test")
}

tasks.withType<Test> {
	useJUnitPlatform()
}
