package siosio

import siosio.entity.PageEntity
import javax.enterprise.context.RequestScoped
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.PathParam

@Path("page")
@RequestScoped
open class PageResource {

  @GET
  @Path("/{memoId}")
  open fun list(@PathParam("memoId") memoId: Long): List<PageEntity> {
    return emptyList()
  }
}
