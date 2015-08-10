name := "HMRC shopping cart"

version := "1.0"

scalaVersion := "2.11.1"

resolvers ++= Seq("snapshots" at "http://oss.sonatype.org/content/repositories/snapshots",
  "releases" at "http://oss.sonatype.org/content/repositories/releases",
  "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases"
  )

libraryDependencies ++= Seq(
	"org.specs2" %% "specs2-core" % "3.0" % "test"
)

scalacOptions in Test ++= Seq("-Yrangepos")
