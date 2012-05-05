package com.github.zhongl.house

import org.scalatest.FunSpec
import instrument.Instrumentation
import java.util.concurrent.{TimeUnit, CountDownLatch}

class AgentSpec extends FunSpec {
  describe("Agent") {
    it("should start closure executor") {
      val arguments = """class.loader.urls=a.jar
       closure.executor.name=com.github.zhongl.house.MockExecutor
       console.address=localhost:54321"""

      Agent.agentmain(arguments, null)

      Flag.latch.await(1L, TimeUnit.SECONDS)
    }
  }
}

object Flag {
  val latch = new CountDownLatch(1)
}

class MockExecutor(consoleAddress: String, inst: Instrumentation) extends Runnable {
  def run() {
    Flag.latch.countDown()
  }
}