plugins {
    id("java")
}

group = "ru.romansib.otus"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation("org.slf4j:slf4j-api:1.7.25")
    implementation("org.slf4j:slf4j-simple:1.7.25")
    implementation("org.projectlombok:lombok:1.18.28")
    implementation ("com.h2database:h2:2.1.210")
    implementation("org.hibernate.orm:hibernate-core:6.6.3.Final")
}

tasks.test {
    useJUnitPlatform()
}