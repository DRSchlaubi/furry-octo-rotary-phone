rootProject.name = "forp"

include("forp-core")
include("forp-find")
include("forp-test-helper")
include("forp-parser")
include("examples")
include("forp-fetch")
include("forp-bom")

pluginManagement {
    resolutionStrategy {
        repositories {
            jcenter()
            gradlePluginPortal()
            maven("https://jitpack.io")
        }
    }
}
include("forp-parser-api")
include("forp-analyze")
include("forp-analyze-api")
include("forp-analyze:forp-analyze-api")
include("forp-analyze:forp-analyze-remote-api")
include("forp-analyze:forp-analyze-core")
include("forp-analyze:forp-analyze-server")
include("forp-analyze:docdex-client")
include("forp-analyze:docdex-client-api")
