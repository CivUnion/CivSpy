plugins {
	id("io.papermc.paperweight.userdev") version "1.3.8"
	id("com.github.johnrengelman.shadow")
	id("xyz.jpenilla.run-paper") version "1.0.6"
}

dependencies {
	paperDevBundle("1.18.2-R0.1-SNAPSHOT")
	implementation(project(":api"))
}

tasks {
	shadowJar {
		exclude("org.slf4j")

		relocate("org.postgresql", "com.programmerdan.minecraft.civspy.repack.postgresql")
		relocate("org.checkerframework", "com.programmerdan.minecraft.civspy.repack.checkerframework")
		relocate("com.zaxxer.hikari", "com.programmerdan.minecraft.civspy.repack.hikari")
	}

	runServer {
		minecraftVersion("1.18")
	}
}
