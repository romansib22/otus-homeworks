plugins {
    id("java")
}

group = "ru.romansib.otus"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation ("org.postgresql:postgresql:42.7.4")
    implementation ("org.hibernate.orm:hibernate-core:6.3.1.Final")
    implementation ("org.liquibase:liquibase-core:4.23.0")

    implementation ("com.zaxxer:HikariCP:6.2.1")
    implementation ("org.hibernate:hibernate-hikaricp:6.6.3.Final")

    implementation ("org.slf4j:slf4j-api:2.0.16")
    runtimeOnly ("ch.qos.logback:logback-classic:1.5.12")
    compileOnly ("org.projectlombok:lombok:1.18.36")
    annotationProcessor ("org.projectlombok:lombok:1.18.36")

    testImplementation (platform("org.junit:junit-bom:5.10.0"))
    testImplementation ("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}