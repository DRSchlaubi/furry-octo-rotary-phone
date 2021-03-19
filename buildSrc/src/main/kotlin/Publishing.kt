import org.gradle.api.Project
import org.gradle.api.publish.PublishingExtension
import org.gradle.api.publish.maven.MavenPublication
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.withType
import org.gradle.plugins.signing.SigningExtension
import java.util.*

fun Project.configurePublishing() {
    apply(plugin = "org.gradle.maven-publish")
    apply(plugin = "org.gradle.signing")

    val configurePublishing: PublishingExtension.() -> Unit = {
        repositories {
            maven {
                setUrl("https://schlaubi.jfrog.io/artifactory/lavakord")

                credentials {
                    username = System.getenv("BINTRAY_USER")
                    password = System.getenv("BINTRAY_KEY")
                }
            }
        }

        publications {
            println("PUB2")
            filterIsInstance<MavenPublication>().forEach { publication ->
                println("PPUB")
                publication.pom {
                    name.set(project.name)
                    description.set("Kotlin library which can, fetch, find, parse and analyze JVM exception stacktraces")
                    url.set("https://github.com/DRSchlaubi/furry-okto-rotary-phone")

                    licenses {
                        license {
                            name.set("Apache-2.0 License")
                            url.set("https://github.com/DRSchlaubi/furry-okto-rotary-phone/blob/main/LICENSE")
                        }
                    }

                    developers {
                        developer {
                            name.set("Michael Rittmeister")
                            email.set("mail@schlaubi.me")
                            organizationUrl.set("https://michael.rittmeister.in")
                        }
                    }

                    scm {
                        connection.set("scm:git:https://github.com/DRSchlaubi/furry-okto-rotary-phone.git")
                        developerConnection.set("scm:git:https://github.com/DRSchlaubi/furry-okto-rotary-phone.git")
                        url.set("https://github.com/DRSchlaubi/furry-okto-rotary-phone")
                    }
                }
            }
        }
    }

    val configureSigning: SigningExtension.() -> Unit = {
        val signingKey = findProperty("signingKey")?.toString()
        val signingPassword = findProperty("signingPassword")?.toString()
        if (signingKey != null && signingPassword != null) {
            useInMemoryPgpKeys(
                String(Base64.getDecoder().decode(signingKey.toByteArray())),
                signingPassword
            )
        }

        publishing.publications.withType<MavenPublication> {
            sign(this)
        }
    }

    extensions.configure("signing", configureSigning)
    extensions.configure("publishing", configurePublishing)
}

val Project.publishing: PublishingExtension
    get() =
        (this as org.gradle.api.plugins.ExtensionAware).extensions.getByName("publishing") as PublishingExtension
