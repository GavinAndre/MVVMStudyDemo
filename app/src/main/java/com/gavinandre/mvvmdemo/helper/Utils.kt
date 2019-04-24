package com.gavinandre.mvvmdemo.helper

import org.jsoup.Jsoup

object Utils {
    
    /**
     * 将文本中的相对地址转换成对应的绝对地址
     * @param content
     * @param baseUrl
     * @return
     */
    fun processImgSrc(content: String, baseUrl: String= Constants.HOST_PAO): String {
        val document = Jsoup.parse(content)
        document.setBaseUri(baseUrl)
        val elements = document.select("img[src]")
        for (el in elements) {
            val imgUrl = el.attr("src")
            if (imgUrl.trim({ it <= ' ' }).startsWith("/")) {
                el.attr("src", el.absUrl("src"))
            }
        }
        return document.html()
    }
}