import org.gradle.kotlin.dsl.`kotlin-dsl`

//不支持 AS 的自动补充功能，也无法使用代码自动跟踪，因此可以考虑使用 buildSrc。
//
//buildSrc 是 Android 项目中一个比较特殊的 project，在 buildSrc 中可以编写 Groovy 语言，但是现在谷歌越来也推荐使用 Kotlin 来编写编译语句。

plugins {
    `kotlin-dsl`
}


repositories { jcenter() }

