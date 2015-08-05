resolvers += Classpaths.sbtPluginReleases

resolvers ++= Seq(
	"Nexus Public Repository" at "http://nexus.despegar.it:8080/nexus/content/groups/public",
	"Nexus Snapshots Repository" at "http://nexus.despegar.it:8080/nexus/content/repositories/snapshots",
	"Nexus Proxies Repository" at "http://nexus.despegar.it:8080/nexus/content/groups/proxies",
	"Sonatype Releases" at "https://oss.sonatype.org/content/repositories/releases/",
	"Sonatype Snapshots" at "http://oss.sonatype.org/content/repositories/snapshots/"
)


// IDE

addSbtPlugin("com.typesafe.sbteclipse" % "sbteclipse-plugin" % "2.4.0")
