package siosio.entity

import java.sql.Timestamp
import javax.persistence.*

@Entity
@Table(name = "memo")
@NamedQuery(
    name = "memo_findAll",
    query = "select m from MemoEntity m order by m.updatedTime desc"
)
public open class MemoEntity() {

  constructor(
      title: String,
      description: String) : this() {
    this.title = title
    this.description = description
  }

  @Id @GeneratedValue @Column(name = "id")
  var memoId: Long? = null

  @Column(nullable = false, length = 100)
  lateinit var title: String

  @Column(nullable = false, length = 200)
  lateinit var description: String

  @Column(nullable = false)
  lateinit var updatedTime:Timestamp

  @OneToMany(fetch = FetchType.LAZY, cascade = arrayOf(CascadeType.REMOVE, CascadeType.ALL))
  lateinit var pages: MutableList<PageEntity>

  @PrePersist
  @PreUpdate
  open fun pre():Unit {
    this.updatedTime = Timestamp(System.currentTimeMillis())
  }
}
