package siosio.memo

import kotlin.properties.*

class MemoForm() {
  var title: String by Delegates.notNull()

  var detail: String by Delegates.notNull()
}