package siosio.page

import siosio.entity.MemoEntity
import siosio.entity.PageEntity
import javax.enterprise.context.RequestScoped
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext
import javax.transaction.Transactional

@RequestScoped
@Transactional
open  class PageService {

  @PersistenceContext
  open var em: EntityManager? = null

  open fun findMemo(memoId:Long):MemoEntity = em!!.find(javaClass<MemoEntity>(), memoId)

  open fun save(memoId:Long, entity: PageEntity) {
    val memoEntity = em!!.find(javaClass<MemoEntity>(), memoId)
    entity.memo = memoEntity
    memoEntity.pages!!.add(entity)
  }

  open fun list(memoId: Long): List<PageEntity> {
    val memo = findMemo(memoId)
    val query = em!!.createNamedQuery("page_findAll", javaClass<PageEntity>())
    query.setParameter("memo", memo)

    return query.getResultList()
  }
}
