import us.ihmc.jros2.generator.jros2GenTask

buildscript {
   repositories {
      maven { url = uri("https://plugins.gradle.org/m2/") }
      maven { url = uri("https://robotlabfiles.ihmc.us/repository/") }
      mavenCentral()
   }
}

plugins {
   id("us.ihmc.ihmc-build")
   id("us.ihmc.log-tools-plugin") version "0.6.3"
   id("us.ihmc.jros2.generator") version "1.1.998"
}

ihmc {
   loadProductProperties("../product.properties")

   configureDependencyResolution()
   configurePublications()
   declareMavenLocal()
}

mainDependencies {
   api("us.ihmc:jros2:1.1.998")
}

sourceSets {
   named("main") {
      java.srcDirs("src/main/java-interfaces")
   }
}

tasks.register<jros2GenTask>("generateMessages") {
   packagePaths = listOf(
      projectDir.resolve("ihmc_interfaces").resolve("atlas_msgs").absolutePath,
      projectDir.resolve("ihmc_interfaces").resolve("behavior_msgs").absolutePath,
      projectDir.resolve("ihmc_interfaces").resolve("controller_msgs").absolutePath,
      projectDir.resolve("ihmc_interfaces").resolve("exoskeleton_msgs").absolutePath,
      projectDir.resolve("ihmc_interfaces").resolve("ihmc_common_msgs").absolutePath,
      projectDir.resolve("ihmc_interfaces").resolve("perception_msgs").absolutePath,
      projectDir.resolve("ihmc_interfaces").resolve("system_monitor_msgs").absolutePath,
      projectDir.resolve("ihmc_interfaces").resolve("test_msgs").absolutePath,
      projectDir.resolve("ihmc_interfaces").resolve("toolbox_msgs").absolutePath,
      projectDir.resolve("ihmc_interfaces").resolve("vision_msgs").absolutePath,
   )
   outputDir = sourceSets["main"].java.srcDirs.find { it.name == "java-interfaces" }.toString()
}