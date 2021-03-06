import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.buildFeatures.dockerSupport
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.*
import jetbrains.buildServer.configs.kotlin.v2019_2.triggers.VcsTrigger.QuietPeriodMode.USE_DEFAULT
import jetbrains.buildServer.configs.kotlin.v2019_2.triggers.finishBuildTrigger
import jetbrains.buildServer.configs.kotlin.v2019_2.triggers.vcs
import jetbrains.buildServer.configs.kotlin.v2019_2.vcs.GitVcsRoot

/*
The settings script is an entry point for defining a TeamCity
project hierarchy. The script should contain a single call to the
project() function with a Project instance or an init function as
an argument.

VcsRoots, BuildTypes, Templates, and subprojects can be
registered inside the project using the vcsRoot(), buildType(),
template(), and subProject() methods respectively.

To debug settings scripts in command-line, run the

    mvnDebug org.jetbrains.teamcity:teamcity-configs-maven-plugin:generate

command and attach your debugger to the port 8000.

To debug in IntelliJ Idea, open the 'Maven Projects' tool window (View
-> Tool Windows -> Maven Projects), find the generate task node
(Plugins -> teamcity-configs -> teamcity-configs:generate), the
'Debug' option is available in the context menu for the task.
*/

version = "2020.2"
project {
    subProject(AsavSyncerProject)

//    buildType(BuildArtifacts)

//    buildTypesOrder = arrayListOf(BuildArtifacts)
}


val dockerImagesLoaderPluginDockerImage = "%dockerDevRepository%/teamcity/docker-images-loader:latest"

val libericaOpenJdkDockerImage = "bellsoft/liberica-openjdk-alpine:11"
val mavenPluginDockerImage = "mavenplugin:latest" // needed for publish template
val dependencyVerifierPluginDockerImage = "teamcity/dependency-verifier:1.0"
val artifactsManagementPluginDockerImage = "teamcity/artifacts-management:1.0"
val dockerImages = arrayListOf(libericaOpenJdkDockerImage,
    mavenPluginDockerImage, dependencyVerifierPluginDockerImage,
    artifactsManagementPluginDockerImage)

//object BuildArtifacts : BuildType({
//
//    name = "Build & Publish to Dev Env"
//
//    type = Type.REGULAR
//
//    val artifactSummary = "build/distributions/published_artifacts.json"
//
//    publishArtifacts = PublishMode.SUCCESSFUL
//    artifactRules = artifactSummary
//
//    params {
//        param("repoUser", "%hseAutomationUser%")
//        param("repoPass", "%hseAutomationPassword%")
//        param("dockerRegistry", "%dockerDevRepository%")
//        param("tcRestAccessToken", "%hseAutomationTCRestToken%")
//        param("publicationRepoUser", "%hseAutomationUser%")
//        param("publicationRepoPassword", "%hseAutomationPassword%")
//        param("publicationRepoUrl", "%mavenThirdPartyRepository%")
//        param("gradleArtifactsBasePath", "build/distributions")
//    }
//
//    steps {
//        exec {
//            id = "PRELOAD_DOCKER_IMAGES"
//            name = "Preload Docker Images"
//            path = "/usr/local/bin/docker-entrypoint.sh"
//            arguments = "load"
//            dockerImage = dockerImagesLoaderPluginDockerImage
//            dockerRunParameters = """
//                    --env "PLUGIN_TC_REGISTRY_URL=%dockerDevRepository%"
//                    --env "PLUGIN_TC_DOCKER_IMAGES=${dockerImages.joinToString(separator = " ")}"
//                    -v /var/run/docker.sock:/var/run/docker.sock
//                    -v %env.HOME%/.docker/config.json:/root/.docker/config.json
//                    """.trimIndent()
//        }
//    }
//
//
//    features {
//        dockerSupport {
//            id = "DockerSupport"
//            cleanupPushedImages = true
//            loginToRegistry = on {
//                dockerRegistryId = "PROJECT_EXT_8"
//            }
//        }
//    }
//})