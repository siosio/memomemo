package siosio.page

import siosio.entity.*
import siosio.service.*
import javax.enterprise.context.RequestScoped
import javax.inject.Inject
import javax.ws.rs.*
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

@Path("page")
@RequestScoped
open class PageResource {

  @Inject
  lateinit open private val service: MemoService

  @GET
  @Path("/list/{memoId}")
  @Produces(MediaType.APPLICATION_JSON)
  open fun list(@PathParam("memoId") memoId: Long): List<PageForm> {
    return service.findAllPage(memoId).mapNotNull {
      PageForm(it.id!!,
          it.title!!,
          it.text!!,
          memoId)
    }
  }

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  open fun create(form: PageForm): Response {
    service.addPage(form.memoId, PageEntity(form.title, form.text))
    return Response.status(Response.Status.CREATED).build()
  }
}
