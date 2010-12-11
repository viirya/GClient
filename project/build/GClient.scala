
import sbt._
import java.io.File

class GClient(info: ProjectInfo) extends AndroidProject(info) {
  override def androidPlatformName = "android-1.5"
  // or preferably set the ANDROID_SDK_HOME environment variable
  override def androidSdkPath = Path.fromFile(new File("/Users/viirya/tools/android-sdk-mac_x86-1.5_r2/"))
}

