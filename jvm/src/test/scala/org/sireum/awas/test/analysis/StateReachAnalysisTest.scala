package org.sireum.awas.test.analysis

import java.util

import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import org.junit.runners.Parameterized.Parameters
import org.sireum.test.{JUnitTestFramework, TestDef}

@RunWith(value = classOf[Parameterized])
final class StateReachAnalysisTest(name: String, td: TestDef) {

  @Test
  def test(): Unit = {
    td.test(JUnitTestFramework)
  }
}

object StateReachAnalysisTest {
  val provider = new StateReachAnalysisTestDefProvider(JUnitTestFramework)

  @Parameters(name = "{0}")
  def parameters: util.ArrayList[Array[Object]] = {
    val ps = provider.enabledTestDefs.map(td => Array(td.name, td))
    val r = new java.util.ArrayList[Array[Object]](ps.size)
    for (p <- ps) {
      r.add(p)
    }
    r
  }
}
