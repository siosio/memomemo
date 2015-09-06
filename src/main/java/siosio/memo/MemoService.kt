package siosio.memo

import siosio.entity.*
import javax.enterprise.context.*
import javax.persistence.*
import javax.transaction.*

@RequestScoped
@Transactional
open class MemoService {

  @PersistenceContext
  open var em: EntityManager? = null

  open fun save(entity: MemoEntity): MemoEntity {
    em!!.persist(entity)
    return entity
  }

  open fun findAll(): List<MemoEntity> {
    val query = em!!.createNamedQuery("memo_findAll", javaClass<MemoEntity>())
    query.setMaxResults(10)
    return query.getResultList()
  }

  open fun delete(memoId: Long) {
    val entity = em!!.find(javaClass<MemoEntity>(), memoId)
    em!!.remove(entity)
  }
}
