package siosio.memo

import siosio.entity.*
import javax.ejb.*
import javax.enterprise.context.*
import javax.inject.*
import javax.ws.rs.*
import javax.ws.rs.core.*

@Path("/memo")
@RequestScoped
open public class MemoResource {

  @Inject
  open var service: MemoService? = null

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  open fun list(): List<MemoEntity> {
    return service!!.findAll()
  }

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  open fun create(memoForm: MemoForm): Response {
    service!!.save(MemoEntity(memoForm.title, memoForm.description))
    return Response.status(Response.Status.CREATED).build()
  }

  @DELETE
  @Path("/{memoId}")
  open fun delete(@PathParam("memoId") memoId: Long): Response {
    service!!.delete(memoId)
    return Response.accepted().build()
  }
}
