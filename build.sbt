// ··· Settings ···


// ··· Project Info ···

name := "mitt"

organization := "com.despegar.ml-scala"

scalaVersion := "2.11.4"

fork in run := true

// ··· Project Enviroment ···

EclipseKeys.createSrc := EclipseCreateSrc.Default + EclipseCreateSrc.Resource

EclipseKeys.projectFlavor := EclipseProjectFlavor.Scala

EclipseKeys.withSource := true

EclipseKeys.executionEnvironment := Some(EclipseExecutionEnvironment.JavaSE17)

unmanagedSourceDirectories in Compile := (scalaSource in Compile).value :: Nil

unmanagedSourceDirectories in Test := (scalaSource in Test).value :: baseDirectory.value / "src/test/it/scala" :: Nil

resourceDirectory in Compile := baseDirectory.value / "src" / "main" / "resources"

unmanagedResourceDirectories in Compile <+= baseDirectory( _ / "src" / "main" / "webapp" )

unmanagedResourceDirectories in Test <+= baseDirectory( _ / "src" / "test" / "it" / "resources" )

// ··· Project Options ···

javacOptions ++= Seq(
    "-source", "1.7",
    "-target", "1.7"
)

scalacOptions ++= Seq(
    "-encoding",
    "utf8",
    "-feature",
    "-language:postfixOps",
    "-language:implicitConversions",
    "-unchecked",
    "-deprecation"
)

// ··· Project Testing ···

testOptions in Test += Tests.Argument(TestFrameworks.Specs2, "junitxml", "console")

parallelExecution in Test := false

// ··· Project Cobertura ···


// ··· Project Resources ···


// ··· Project Repositories ···

resolvers ++= Seq(
  "Nexus Public Repository"        at "http://nexus.despegar.it:8080/nexus/content/groups/public",
  "Nexus Snapshots Repository"     at "http://nexus.despegar.it:8080/nexus/content/repositories/snapshots",
  "Nexus Releases Repository"      at "http://nexus.despegar.it:8080/nexus/content/repositories/releases",
  "spray repo"                     at "http://repo.spray.io/",
  "OSS"                            at "http://oss.sonatype.org/content/repositories/releases/")

// ··· Project Dependancies···

libraryDependencies ++= Seq(
  "org.scala-lang"                %   "scala-reflect"         % "2.11.6",
  "org.scala-lang.modules"        %%   "scala-async"           % "0.9.3",
  "commons-logging"               %   "commons-logging"       % "1.2",
  "org.slf4j"                     %   "slf4j-api"             % "1.7.7" ,
  "ch.qos.logback"                %   "logback-classic"       % "1.1.2" excludeAll(
    ExclusionRule(organization = "org.slf4j")),
  "org.jfree"                     %   "jfreechart"            % "1.0.19",
  "org.scalatest"                 % "scalatest_2.11"          % "2.2.5",
  "junit"                         %   "junit"                 % "4.11"      % "test"
)
  .map(_.excludeAll(
  ExclusionRule(organization ="com.sun.jmx"),
  ExclusionRule(organization ="com.sun.jdmk"),
  ExclusionRule(organization ="javax.jms"),
  ExclusionRule(organization ="org.jboss.netty")
  ))

// ··· Project Tasks ···


val defaultJvmOpts = " -ea -server -XX:+UseG1GC -XX:+TieredCompilation -XX:+UseCompressedOops -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=dumps"


