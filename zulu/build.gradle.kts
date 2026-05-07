import org.gradle.api.Project
import org.gradle.api.tasks.testing.Test

plugins {
   id("us.ihmc.ihmc-build")
   id("us.ihmc.scs") version "0.4"
   id("us.ihmc.log-tools-plugin") version "0.6.3"
}

apply(from = "../gradle/java-compile-encoding.gradle.kts")

val allocationInstrumentationAgentDependency = "com.google.code.java-allocation-instrumenter:java-allocation-instrumenter:3.3.4"
val allocationTestCategories = setOf("allocation-slow", "allocation-slow-2")

fun Project.configureAllocationInstrumentationAgent()
{
   val project = this
   project.plugins.withId("java") {
      val allocationInstrumentationAgent = project.configurations.create("allocationInstrumentationAgent") {
         isCanBeConsumed = false
         isCanBeResolved = true
      }

      project.dependencies.add(allocationInstrumentationAgent.name, allocationInstrumentationAgentDependency)
      project.dependencies.add("testRuntimeOnly", allocationInstrumentationAgentDependency)

      project.configurations.matching {
         it.name == "testRuntimeClasspath" || it.name == "testCompileClasspath" || it.name == allocationInstrumentationAgent.name
      }.configureEach {
         resolutionStrategy.force(allocationInstrumentationAgentDependency)
      }

      project.tasks.withType<Test>().configureEach {
         val category = project.providers.gradleProperty("category").orNull
         if (category in allocationTestCategories)
         {
            inputs.files(allocationInstrumentationAgent).withPropertyName("allocationInstrumentationAgent")
            doFirst {
               val agentJar = allocationInstrumentationAgent.files.single {
                  it.name.startsWith("java-allocation-instrumenter-") && it.extension == "jar"
               }
               jvmArgs("-javaagent:${agentJar.absolutePath}")
            }
         }
      }
   }
}

configureAllocationInstrumentationAgent()
subprojects {
   configureAllocationInstrumentationAgent()
}

ihmc {
   loadProductProperties("../product.properties")
   
   configureDependencyResolution()
   configurePublications()
}

mainDependencies {
   api("us.ihmc:ihmc-avatar-interfaces-visualizers:source")
   api("us.ihmc:ihmc-model-file-loader:source")
   api("us.ihmc:ihmc-manipulation-planning:source")
   api("us.ihmc:ihmc-footstep-planning-visualizers:source")
   api("us.ihmc:ihmc-high-level-behaviors:source")
}

testDependencies {
   api("us.ihmc:ihmc-avatar-interfaces-test:source")
   api("us.ihmc:ihmc-sensor-processing-test:source")
   api("us.ihmc:ihmc-simulation-toolkit-test:source")
}
