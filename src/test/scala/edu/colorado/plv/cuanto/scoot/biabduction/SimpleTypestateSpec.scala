package edu.colorado.plv.cuanto.scoot.biabduction

import edu.colorado.plv.cuanto.scoot.sootloading.SootLoading
import org.scalatest.{FlatSpec, Matchers}
import org.scalatest.prop.PropertyChecks
import soot.{Scene, SootClass}

import scala.collection.JavaConverters._
import scala.util.Success

/**
  * Created by s on 4/30/17.
  */
class SimpleTypestateSpec extends FlatSpec with Matchers with PropertyChecks {
  val fileSep: String = System.getProperty("file.separator")

  val testfile = System.getProperty("user.dir") + fileSep + "src" + fileSep + "test" + fileSep + "resources" + fileSep +
    "test_files"+ fileSep + "state_tests"
  val simpleTypestateRunner = (className: String, methodName: String, scene: Scene) => {
//    val filter: Iterable[SootClass] = scene.getClasses.asScala.filter(c => c.getName == "test1")
    val clazz = scene.getSootClass(className)
    val method = clazz.getMethodByName(methodName)
    SimpleTypestate.processMethod(method)
  }
  "processMethod" should "handle an empty object" in {
    val mainRun: (Scene) => SimpleTypestate.MethodTriple = simpleTypestateRunner("test1","main",_)

    val res = SootLoading.getAnalysisResult(List(testfile), Some("test1"), mainRun)

  }
  "processMethod" should "handle a method with an object allocation" in {
    val mainRun: (Scene) => SimpleTypestate.MethodTriple = simpleTypestateRunner("test2","main",_)
    val res = SootLoading.getAnalysisResult(List(testfile), Some("test2"), mainRun)
    res shouldBe a[Success[_]]
  }
}
