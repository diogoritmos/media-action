plugins {
    id("java")
    id("application")
    id("co.uzzu.dotenv.gradle") version "4.0.0"
}

group = "br.com.diogoritmos"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.apache.tika:tika-core:2.4.0")

    // Requires explicit configuration
    implementation(mapOf(
        "group" to "com.microsoft.cognitiveservices.speech",
        "name" to "client-sdk",
        "version" to "1.40.0",
        "ext" to "jar"
    ))

    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

application {
    mainClass.set("br.com.diogoritmos.Main")
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<JavaExec> {
    environment("SPEECH_KEY", env.SPEECH_KEY.value)
    environment("SPEECH_REGION", env.SPEECH_REGION.value)
}