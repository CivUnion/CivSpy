import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
	`java-library`
	`maven-publish`
	id("com.adarshr.test-logger") version "3.2.0" apply false
	id("com.github.johnrengelman.shadow") version "7.1.0" apply false
}

gradle.buildFinished {
	project.buildDir.deleteRecursively()
}

subprojects {
	apply(plugin = "java-library")
	apply(plugin = "maven-publish")
	apply(plugin = "com.adarshr.test-logger")

	java {
		toolchain.languageVersion.set(JavaLanguageVersion.of(17))
		withJavadocJar()
		withSourcesJar()
	}

	repositories {
		mavenCentral()
		maven("https://oss.sonatype.org/content/repositories/snapshots")
	}

	dependencies {
		testImplementation("org.junit.jupiter:junit-jupiter-api:5.2.0")
		testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.2.0")
	}

	tasks {
		compileJava {
			options.encoding = Charsets.UTF_8.name()
			options.release.set(17)
		}
		processResources {
			filteringCharset = Charsets.UTF_8.name()
			filesMatching("**/plugin.yml") {
				expand( project.properties )
			}
		}
	}

	afterEvaluate {
		tasks.withType<ShadowJar> {
			// Overwrite default jar
			archiveClassifier.set("")
		}

		tasks.findByName("shadowJar")?.also {
			tasks.named("assemble") { dependsOn(it) }
		}
	}

	publishing {
		repositories {
			maven {
				url = uri("https://nexus.civunion.com/repository/maven-releases/")
				credentials {
					username = System.getenv("REPO_USERNAME")
					password = System.getenv("REPO_PASSWORD")
				}
			}
		}
		publications {
			register<MavenPublication>("main") {
				from(components["java"])
			}
		}
	}
}
