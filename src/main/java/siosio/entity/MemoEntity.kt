package siosio.entity

import java.sql.Timestamp
import javax.persistence.*

@Entity
@Table(name = "memo")
@NamedQuery(
    name = "memo_top10",
    query = "select m from MemoEntity m order by m.updatedTime desc"
)
public open class MemoEntity() {

  constructor(
      title: String,
      detail: String) : this() {
    this.title = title
    this.detail = detail
  }

  var memoId: Long? = null
    @Id @GeneratedValue @Column(name = "id") get

  var title: String? = null
    @Column(nullable = false, length = 100) get

  var detail: String? = null
    @Column(nullable = false, length = 200) get

  var updatedTime:Timestamp? = null
    @Column(nullable = false) get

  @PrePersist
  @PreUpdate
  open fun pre():Unit {
    this.updatedTime = Timestamp(System.currentTimeMillis())
  }
}
