import jetbrains.buildServer.configs.kotlin.v2019_2.BuildType
import jetbrains.buildServer.configs.kotlin.v2019_2.Project
import jetbrains.buildServer.configs.kotlin.v2019_2.PublishMode
import jetbrains.buildServer.configs.kotlin.v2019_2.buildFeatures.dockerSupport
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.exec


//val dockerImagesLoaderPluginDockerImage = "%dockerDevRepository%/teamcity/docker-images-loader:latest"
//
//val libericaOpenJdkDockerImage = "bellsoft/liberica-openjdk-alpine:11"
//val mavenPluginDockerImage = "mavenplugin:latest" // needed for publish template
//val dependencyVerifierPluginDockerImage = "teamcity/dependency-verifier:1.0"
//val artifactsManagementPluginDockerImage = "teamcity/artifacts-management:1.0"
//val dockerImages = arrayListOf(libericaOpenJdkDockerImage,
//    mavenPluginDockerImage, dependencyVerifierPluginDockerImage,
//    artifactsManagementPluginDockerImage)

object TomatoeProject : Project({
    name = "TomatoeProject"
    buildType(BuildArtifacts)
})

object TomatoeBuildArtifacts : BuildType({

    name = "Build & Publish to Dev Env"

    type = Type.REGULAR

    val artifactSummary = "build/distributions/published_artifacts.json"

    publishArtifacts = PublishMode.SUCCESSFUL
    artifactRules = artifactSummary

    params {
        param("repoUser", "%hseAutomationUser%")
        param("repoPass", "%hseAutomationPassword%")
        param("dockerRegistry", "%dockerDevRepository%")
        param("tcRestAccessToken", "%hseAutomationTCRestToken%")
        param("publicationRepoUser", "%hseAutomationUser%")
        param("publicationRepoPassword", "%hseAutomationPassword%")
        param("publicationRepoUrl", "%mavenThirdPartyRepository%")
        param("gradleArtifactsBasePath", "build/distributions")
    }

    steps {
        exec {
            id = "PRELOAD_DOCKER_IMAGES"
            name = "Preload Docker Images"
            path = "/usr/local/bin/docker-entrypoint.sh"
            arguments = "load"
            dockerImage = dockerImagesLoaderPluginDockerImage
            dockerRunParameters = """
                    --env "PLUGIN_TC_REGISTRY_URL=%dockerDevRepository%"
                    --env "PLUGIN_TC_DOCKER_IMAGES=${dockerImages.joinToString(separator = " ")}"
                    -v /var/run/docker.sock:/var/run/docker.sock
                    -v %env.HOME%/.docker/config.json:/root/.docker/config.json
                    """.trimIndent()
        }
    }


    features {
        dockerSupport {
            id = "DockerSupport"
            cleanupPushedImages = true
            loginToRegistry = on {
                dockerRegistryId = "PROJECT_EXT_8"
            }
        }
    }
})