import sbt._
import Keys._
import PlayProject._

object ApplicationBuild extends Build {

    val appName         = "voortgangssysteem"
    val appVersion      = "1.0-SNAPSHOT"

    val appDependencies = Seq(
       "mysql" % "mysql-connector-java" % "5.1.18",
       "org.json" % "json" % "20090211",
       "org.codehaus.sonar" % "sonar-ws-client" % "2.14",
       "org.apache.httpcomponents" % "httpclient"% "4.1.2"
    )

    val main = PlayProject(appName, appVersion, appDependencies, mainLang = JAVA).settings(
      // Add your own project settings here      
    )

}
