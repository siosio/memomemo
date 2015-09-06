package siosio.page

import kotlin.properties.Delegates

public class PageForm() {

  constructor(pageId: Long, title: String, text: String, memoId: Long) : this() {
    this.pageId = pageId
    this.title = title
    this.text = text
    this.memoId = memoId
  }

  var pageId: Long by Delegates.notNull()

  var memoId: Long by Delegates.notNull()

  var title: String by Delegates.notNull()

  var text: String by Delegates.notNull()
}
