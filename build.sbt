name := "ewa-play-intro"

version := "1.0-SNAPSHOT"

libraryDependencies ++= Seq(
  javaJdbc,
  javaCore,
  javaJpa,
  cache,
  "org.hibernate" % "hibernate-entitymanager" % "4.3.1.Final"
)    

play.Project.playJavaSettings
