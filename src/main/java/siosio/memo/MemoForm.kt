package siosio.memo

import kotlin.properties.*

class MemoForm() {

  constructor(memoId:Long, title: String, description: String) : this() {
    this.memoId = memoId
    this.title = title
    this.description = description
  }

  var memoId:Long by Delegates.notNull()

  var title: String by Delegates.notNull()

  var description: String by Delegates.notNull()
}
