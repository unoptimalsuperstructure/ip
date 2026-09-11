plugins {
    java
    application
    id("org.openjfx.javafxplugin") version "0.1.0"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.14.4"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

javafx {
    version = "17.0.7"
    modules("javafx.controls", "javafx.fxml")
}

application {
    mainClass.set("sanyueqi.ui.Main")
}

tasks.named<JavaExec>("run") {
    enableAssertions = true
}

tasks.test {
    useJUnitPlatform()
}