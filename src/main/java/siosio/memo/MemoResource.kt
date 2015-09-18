package siosio.memo

import siosio.entity.MemoEntity
import siosio.service.MemoService
import javax.enterprise.context.RequestScoped
import javax.inject.Inject
import javax.ws.rs.*
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

@Path("/memo")
@RequestScoped
open public class MemoResource {

  @Inject
  lateinit open private val service: MemoService

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  open fun list(): List<MemoForm> {
    val list = service.findAll()
    return list.mapNotNull {
      MemoForm(it.memoId!!, it.title, it.description)
    }
  }

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  open fun create(memoForm: MemoForm): Response {
    service.save(MemoEntity(memoForm.title, memoForm.description))
    return Response.status(Response.Status.CREATED).build()
  }

  @DELETE
  @Path("/{memoId}")
  open fun delete(@PathParam("memoId") memoId: Long): Response {
    service.delete(memoId)
    return Response.accepted().build()
  }
}
