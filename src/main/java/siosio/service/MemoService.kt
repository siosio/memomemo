package siosio.service

import siosio.entity.*
import javax.enterprise.context.*
import javax.persistence.*
import javax.transaction.*

@RequestScoped
@Transactional
open class MemoService {

  @PersistenceContext
  lateinit open private val em: EntityManager

  open fun find(memoId:Long): MemoEntity = em.find(MemoEntity::class.java, memoId)

  open fun save(entity: MemoEntity): MemoEntity {
    em.persist(entity)
    return entity
  }

  open fun addPage(memoId: Long, page: PageEntity) {
    val memo = find(memoId)
    memo.pages.add(page)
  }

  open fun findAll(): List<MemoEntity> {
    val query = em.createNamedQuery("memo_findAll", MemoEntity::class.java)
    query.setMaxResults(10)
    return query.resultList
  }

  open fun findAllPage(memoId:Long) : List<PageEntity> {
    return find(memoId).pages
  }

  open fun delete(memoId: Long) {
    val entity = em.find(MemoEntity::class.java, memoId)
    em.remove(entity)
  }
}
