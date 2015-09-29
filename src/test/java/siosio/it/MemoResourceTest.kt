package siosio.it

import org.eclipse.persistence.jaxb.rs.*
import org.glassfish.hk2.api.*
import org.glassfish.hk2.utilities.binding.*
import org.glassfish.jersey.client.*
import org.glassfish.jersey.server.*
import org.glassfish.jersey.test.*
import org.hamcrest.collection.*
import org.hamcrest.core.*
import org.junit.*
import siosio.entity.*
import siosio.memo.*
import siosio.service.*
import java.util
import java.util.*
import javax.ws.rs.core.*
import kotlin.properties.*

public class MemoResourceTest : JerseyTest() {

  override fun configure(): Application? {
    enable(TestProperties.DUMP_ENTITY)
    enable(TestProperties.LOG_TRAFFIC)
    return ResourceConfig(MemoResource::class.java)
        .register(object : AbstractBinder () {
          override fun configure() {
            bindFactory(object : Factory<MemoService> {
              override fun dispose(instance: MemoService?) {
              }

              override fun provide(): MemoService? {
                return MemoServiceMock()
              }
            }).to(MemoService::class.java)
          }
        })
  }

  override fun configureClient(config: ClientConfig) {
    config.register(MOXyJsonProvider::class.java)
  }

  class MemoServiceMock : MemoService() {
    companion object {
      var findAllResult: List<MemoEntity> by Delegates.notNull()
    }
    override fun findAll(): List<MemoEntity> {
      return findAllResult
    }
  }

  @Test
  fun `データが存在しない場合からのリストが戻されること`() {
    MemoServiceMock.findAllResult = listOf()
    val result = target("memo")
        .request()
        .accept(MediaType.APPLICATION_JSON_TYPE)
        .get()
    Assert.assertThat(result.status, Is.`is`(200))
    Assert.assertThat(result.mediaType, Is.`is`(MediaType.APPLICATION_JSON_TYPE))
    val entity = result.readEntity(object : GenericType<ArrayList<MemoForm>>() {})
    Assert.assertThat(entity, IsEmptyCollection.empty())
  }
}