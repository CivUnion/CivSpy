pluginManagement {
	repositories {
		gradlePluginPortal()
		maven("https://papermc.io/repo/repository/maven-public/")
	}
}

include(":api")
include(":platform:bungee")
include(":platform:paper")

project(":platform:bungee").name = rootProject.name + "-bungee"
project(":platform:paper").name = rootProject.name + "-paper"
