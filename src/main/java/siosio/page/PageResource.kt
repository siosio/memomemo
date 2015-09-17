package siosio.page

import siosio.entity.PageEntity
import siosio.service.PageService
import javax.enterprise.context.RequestScoped
import javax.inject.Inject
import javax.ws.rs.*
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

@Path("page")
@RequestScoped
open class PageResource {

  @Inject
  lateinit open private  val service: PageService

  @GET
  @Path("/list/{memoId}")
  @Produces(MediaType.APPLICATION_JSON)
  open fun list(@PathParam("memoId") memoId: Long): List<PageForm> {
    return service.list(memoId).mapNotNull {
      PageForm(it.id!!, it.title!!, it.text!!, it.memo!!.memoId!!)
    }
  }

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  open fun create(form: PageForm): Response {
    val page = PageEntity(form.title, form.text)
    service.save(form.memoId, page)
    return Response.status(Response.Status.CREATED).build()
  }
}
