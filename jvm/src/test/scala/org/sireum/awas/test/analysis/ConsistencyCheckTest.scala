package org.sireum.awas.test.analysis

import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import org.junit.runners.Parameterized.Parameters
import org.sireum.test.{JUnitTestFramework, TestDef}

@RunWith(value = classOf[Parameterized])
final class ConsistencyCheckTest(name: String, td: TestDef) {
  @Test
  def test(): Unit = {
    td.test(JUnitTestFramework)
  }
}

object ConsistencyCheckTest {
  val provider = new ConsistencyCheckTestDefProvider(JUnitTestFramework)

  @Parameters(name = "{0}")
  def parameters = {
    val ps = provider.enabledTestDefs.map(td => Array(td.name, td))
    val r = new java.util.ArrayList[Array[Object]](ps.size)
    for (p <- ps) {
      r.add(p)
    }
    r
  }
}