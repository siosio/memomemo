package siosio.entity

import javax.persistence.*

@Entity
@Table(name = "page")
public open class PageEntity {

  var id:Long? = null
    @Id @GeneratedValue get

  var title:String? = null
    @Column(nullable = false, length = 50) get
}
