package siosio.service

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
  lateinit open private val em: EntityManager

  open fun findMemo(memoId:Long): MemoEntity = em.find(MemoEntity::class.java, memoId)

  open fun save(memoId:Long, entity: PageEntity) {
    val memoEntity = em.find(MemoEntity::class.java, memoId)
    entity.memo = memoEntity
    memoEntity.pages.add(entity)
  }

  open fun list(memoId: Long): List<PageEntity> {
    val memo = findMemo(memoId)
    val query = em.createNamedQuery("page_findAll", PageEntity::class.java)
    query.setParameter("memo", memo)

    val pages = query.resultList
    return pages
  }
}
