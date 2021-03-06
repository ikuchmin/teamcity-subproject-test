import jetbrains.buildServer.configs.kotlin.v2019_2.BuildType
import jetbrains.buildServer.configs.kotlin.v2019_2.Project
import jetbrains.buildServer.configs.kotlin.v2019_2.PublishMode
import jetbrains.buildServer.configs.kotlin.v2019_2.buildFeatures.dockerSupport
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.exec


val dockerImagesLoaderPluginDockerImage = "%dockerDevRepository%/teamcity/docker-images-loader:latest"

val libericaOpenJdkDockerImage = "bellsoft/liberica-openjdk-alpine:11"
val mavenPluginDockerImage = "mavenplugin:latest" // needed for publish template
val dependencyVerifierPluginDockerImage = "teamcity/dependency-verifier:1.0"
val artifactsManagementPluginDockerImage = "teamcity/artifacts-management:1.0"
val dockerImages = arrayListOf(libericaOpenJdkDockerImage,
    mavenPluginDockerImage, dependencyVerifierPluginDockerImage,
    artifactsManagementPluginDockerImage)

object AsavSyncerProject : Project({
    name = "AsavSyncerProject"
//    buildType(BuildArtifacts)
})