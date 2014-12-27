name := "pacemakerplay"

version := "1.0-SNAPSHOT"

libraryDependencies ++= Seq(
  javaJdbc,
  javaEbean,
  cache,
  "net.sf.flexjson" % "flexjson" % "3.1",
  "postgresql" % "postgresql" % "9.1-901-1.jdbc4",
  "joda-time" %% "joda-time" % "2.6"
)     

play.Project.playJavaSettings
