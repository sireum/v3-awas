package org.sireum.awas.test.slang

import java.util

import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import org.junit.runners.Parameterized.Parameters
import org.sireum.test.{JUnitTestFramework, TestDef}

@RunWith(value = classOf[Parameterized])
final class Aadl2AwasGenTest(name: String, td: TestDef) {
  @Test
  def test(): Unit = {
    td.test(JUnitTestFramework)
  }
}

object Aadl2AwasGenTest {
  val provider = new Aadl2AwasGenTestDefProvider(JUnitTestFramework)

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
