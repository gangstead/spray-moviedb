organization  := "com.example"

version       := "0.1"

scalaVersion  := "2.10.3"

scalacOptions := Seq("-unchecked", "-deprecation", "-encoding", "utf8", "-feature")

resolvers ++= Seq(
  "spray repo" at "http://repo.spray.io/",
  "SpringSource Milestone Repository" at "http://repo.springsource.org/milestone" 
)

libraryDependencies ++= {
  val akkaV = "2.2.3"
  val sprayV = "1.2.0"
  Seq(
    "io.spray"            %   "spray-servlet"     % sprayV,
    "io.spray"            %   "spray-routing"     % sprayV,
    "io.spray"            %   "spray-testkit"     % sprayV,
    "io.spray"            %   "spray-client"      % sprayV,    
    "io.spray"            %   "spray-util"        % sprayV,  
    "io.spray"            %   "spray-caching"     % sprayV,
    "io.spray"            %%  "spray-json"        % "1.2.5",
    "com.typesafe.akka"   %%  "akka-slf4j"        % "2.1.4",
    "ch.qos.logback"      %   "logback-classic"   % "1.0.13",
    "org.eclipse.jetty"       %   "jetty-webapp"  % "8.1.13.v20130916"    % "container",
    "org.eclipse.jetty"       %   "jetty-plus"    % "8.1.13.v20130916"    % "container",
    "org.eclipse.jetty.orbit" %   "javax.servlet" % "3.0.0.v201112011016" % "container"  artifacts Artifact("javax.servlet", "jar", "jar"),
    "com.typesafe.akka"   %%  "akka-actor"    % akkaV,
    "com.typesafe.akka"   %%  "akka-testkit"  % akkaV,
    "org.specs2"          %%  "specs2"        % "2.2.3" % "test",
    "org.springframework.scala" % "spring-scala" % "1.0.0.M2",
    "org.springframework" % "spring-web" % "3.2.2.RELEASE",
    "javax.inject" % "javax.inject" % "1"
  )
}

seq(webSettings: _*)

env in Compile := Some(file(".") / "jetty-env.xml" asFile)
