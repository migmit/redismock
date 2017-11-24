import scalariform.formatter.preferences._
import com.typesafe.sbt.SbtScalariform.ScalariformKeys
import org.scalastyle.sbt.ScalastylePlugin

name := "redismock"

version := "1.0.0"

organization := "com.migmit"

scalaVersion := "2.11.8"

crossScalaVersions := Seq("2.11.8")

scalacOptions ++= Seq(
    "-unchecked",            // Show details of unchecked warnings.
    "-deprecation",          // Show details of deprecation warnings.
    "-feature",              // Show details of feature warnings.
    "-Xfatal-warnings",      // All warnings should result in a compiliation failure.
    "-Ywarn-dead-code",      // Fail when dead code is present. Prevents accidentally unreachable code.
    "-encoding", "UTF-8",    // Set correct encoding for Scaladoc.
    "-Xfuture",              // Disables view bounds, adapted args, and unsound pattern matching in 2.11.
    "-Yno-adapted-args",     // Prevent implicit tupling of arguments.
    "-Ywarn-value-discard",  // Prevent accidental discarding of results in unit functions.
    "-Xmax-classfile-name", "140"
)

javacOptions ++= Seq(
    "-Xlint:deprecation"
)

incOptions := incOptions.value.withNameHashing(true)

updateOptions := updateOptions.value.withCachedResolution(true)

shellPrompt in ThisBuild := { state => Project.extract(state).currentRef.project + "> " }

val akkaVersion = "2.3.12"

libraryDependencies ++= Seq(
    "com.typesafe.akka" %% "akka-actor" % akkaVersion % Provided/*,
    // Test dependencies
    "org.specs2" %% "specs2-core" % "3.7" % Test,
    "org.specs2" %% "specs2-junit" % "3.7" % Test,
    "org.specs2" %% "specs2-mock" % "3.7" % Test,
    "org.specs2" %% "specs2-scalacheck" % "3.7" % Test*/
)

// code formatting
SbtScalariform.scalariformSettings ++ Seq(
    ScalariformKeys.preferences := ScalariformKeys.preferences.value
        .setPreference(IndentWithTabs, true)
        .setPreference(DanglingCloseParenthesis, Preserve)
        .setPreference(DoubleIndentClassDeclaration, false)
)

// Scala linting to help preventing bugs
wartremoverErrors in (Compile, compile) ++= Warts.allBut(
    Wart.Throw,
    Wart.DefaultArguments,
    Wart.NoNeedForMonad,
    Wart.Overloading,
    Wart.Equals
)

def getEnvOrDefault(key: String, default: String): String = {
    if (System.getenv().containsKey(key)) {
        System.getenv(key)
    } else {
        default
    }
}

val CI_BUILD = System.getProperty("JENKINS_BUILD") == "true"

/*
publishTo <<= (version)(version =>
    if (CI_BUILD) {
        if (version endsWith "SNAPSHOT") Some("Kinja Snapshots" at sys.env.get("KINJA_SNAPSHOTS_REPO").getOrElse("https://kinjajfrog.jfrog.io/kinjajfrog/kinja-local-snapshots/"))
        else                             Some("Kinja Releases" at sys.env.get("KINJA_RELEASES_REPO").getOrElse("https://kinjajfrog.jfrog.io/kinjajfrog/kinja-local-releases/"))
    } else {
        None
    }
)
 */
resolvers += "Scalaz Bintray Repo" at "http://dl.bintray.com/scalaz/releases"
