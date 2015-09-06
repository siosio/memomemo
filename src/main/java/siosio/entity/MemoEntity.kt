package siosio.entity

import javax.persistence.*

@Entity
@Table(name = "memo")
@NamedQuery(
    name = "memo_top10",
    query = "select m from MemoEntity m"
)
public class MemoEntity() {

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
    @Column(nullable = false, length = 1000) get
}