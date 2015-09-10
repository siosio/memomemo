package siosio.entity

import javax.persistence.*

@Entity
@Table(name = "page")
@NamedQuery(
    name = "page_findAll",
    query = "select p from PageEntity p where p.memo = :memo"
)
public open class PageEntity() {

  constructor(title:String, text:String):this() {
    this.title = title
    this.text = text
  }

  constructor(title:String, text:String, memo:MemoEntity):this() {
    this.title = title
    this.text = text
    this.memo = memo
  }
  var id:Long? = null
    @Id @GeneratedValue get

  var title:String? = null
    @Column(nullable = false, length = 50) get

  var text:String? = null
    @Lob @Column(nullable = false) get

  var memo:MemoEntity? = null
    @ManyToOne(cascade = arrayOf(CascadeType.ALL)) get
}
