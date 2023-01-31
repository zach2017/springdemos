import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
plugins {
	id("org.springframework.boot") version "3.0.2"
	id("io.spring.dependency-management") version "1.1.0"
    id("io.github.kobylynskyi.graphql.codegen") version "5.5.0"
	id("com.gorylenko.gradle-git-properties") version "2.4.1"
	kotlin("jvm") version "1.7.22"
	kotlin("plugin.spring") version "1.7.22"
}

group = "zac"
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
	implementation("org.springframework.boot:spring-boot-starter-actuator")
	implementation("org.springframework.boot:spring-boot-starter-graphql")
	implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-webflux")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
	implementation("javax.validation:validation-api:2.0.1.Final")

	//implementation("io.github.kobylynskyi.graphql.codegen:graphql-codegen-gradle-plugin")
	implementation ("com.graphql-java-kickstart:graphql-spring-boot-starter:11.0.0")
	implementation ("com.graphql-java-kickstart:graphiql-spring-boot-starter:11.0.0")
	implementation ("com.graphql-java:graphql-java-extended-scalars:16.0.1")
	implementation("org.springframework.kafka:spring-kafka")
	annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("io.projectreactor:reactor-test")
	testImplementation("org.springframework.graphql:spring-graphql-test")
	testImplementation("org.springframework.kafka:spring-kafka-test")
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "17"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}


// Automatically generate GraphQL code on project build:
sourceSets {
	getByName("main").java.srcDirs("$buildDir/generated")
}

// Add generated sources to your project source sets:
tasks.named<JavaCompile>("compileJava") {
	dependsOn("graphqlCodegen")
}

tasks.named<io.github.kobylynskyi.graphql.codegen.gradle.GraphQLCodegenGradleTask>("graphqlCodegen") {
	outputDir = File("$buildDir/generated")
	apiPackageName = "zac.graphql.api"
	//modelPackageName = "zac.graphql.model"
	parentInterfaces {
		queryResolver = "graphql.kickstart.tools.GraphQLQueryResolver"
		mutationResolver = "graphql.kickstart.tools.GraphQLMutationResolver"
		subscriptionResolver = "graphql.kickstart.tools.GraphQLSubscriptionResolver"
		resolver = "graphql.kickstart.tools.GraphQLResolver<{{TYPE}}>"
	}
}
