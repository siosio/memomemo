package siosio.memo

import kotlin.properties.*

class MemoForm() {
  var title: String by Delegates.notNull()

  var description: String by Delegates.notNull()
}
