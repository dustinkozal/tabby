buildscript {
   repositories {
      mavenCentral()
      mavenLocal()
      maven("https://dl.bintray.com/kotlin/kotlin-eap")
      maven("https://kotlin.bintray.com/kotlinx")
      gradlePluginPortal()
   }
}

plugins {
   java
   kotlin("multiplatform") version Libs.kotlinVersion
   id("java-library")
   id("maven-publish")
   signing
   id("org.jetbrains.dokka") version Libs.dokkaVersion
}

tasks {
   javadoc {
   }
}

allprojects {

   repositories {
      mavenCentral()
      jcenter()
      google()
      maven("https://kotlin.bintray.com/kotlinx")
      maven("https://dl.bintray.com/kotlin/kotlin-eap")
   }

   group = "com.sksamuel.tabby"
   version = Ci.publishVersion
}

kotlin {
   targets {
      jvm {
         compilations.all {
            kotlinOptions {
               jvmTarget = "1.8"
               kotlinOptions.freeCompilerArgs += "-Xopt-in=kotlin.RequiresOptIn"
               kotlinOptions.freeCompilerArgs += "-Xopt-in=kotlin.Experimental"
               kotlinOptions.freeCompilerArgs += "-Xopt-in=kotlin.experimental.ExperimentalTypeInference"
               kotlinOptions.freeCompilerArgs += "-Xopt-in=kotlin.time.ExperimentalTime"
            }
         }
      }
   }
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
   kotlinOptions.jvmTarget = "1.8"
   kotlinOptions.apiVersion = "1.4"
   kotlinOptions.freeCompilerArgs += "-Xopt-in=kotlin.RequiresOptIn"
   kotlinOptions.freeCompilerArgs += "-Xopt-in=kotlin.Experimental"
   kotlinOptions.freeCompilerArgs += "-Xopt-in=kotlin.experimental.ExperimentalTypeInference"
   kotlinOptions.freeCompilerArgs += "-Xopt-in=kotlin.time.ExperimentalTime"
   kotlinOptions {
      kotlinOptions.freeCompilerArgs += "-Xopt-in=kotlin.RequiresOptIn"
      kotlinOptions.freeCompilerArgs += "-Xopt-in=kotlin.Experimental"
      kotlinOptions.freeCompilerArgs += "-Xopt-in=kotlin.experimental.ExperimentalTypeInference"
      kotlinOptions.freeCompilerArgs += "-Xopt-in=kotlin.time.ExperimentalTime"
   }
}

val publications: PublicationContainer = (extensions.getByName("publishing") as PublishingExtension).publications

signing {
   useGpgCmd()
   if (Ci.isRelease)
      sign(publications)
}
