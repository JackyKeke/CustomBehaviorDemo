// 版本信息
object Version {


    //    AppConfig
    const val compileSdk = 29
    const val buildTools = "29.0.3"
    const val minSdk = 21
    const val targetSdk = 29
    const val versionCode = 1
    const val versionName = "1.0.0"


    const val core_ktx = "1.3.0"
    const val appcompat = "1.1.0"
    const val constraintlayout = "1.1.3"
    const val junit = "4.12"
    const val ext_junit = "1.1.1"
    const val espresso_core = "3.2.0"

    const val palette = "1.0.0"
    const val statusbarutil = "1.5.1"
    const val BaseRecyclerViewAdapterHelper = "2.9.40"
}


//依赖库名称
object Deps {
    const val coreKtx = "androidx.core:core-ktx:${Version.core_ktx}"

    const val appcompat = "androidx.appcompat:appcompat:${Version.appcompat}"
    const val constraintlayout =
            "androidx.constraintlayout:constraintlayout:${Version.constraintlayout}"
    const val junit = "junit:junit:${Version.junit}"
    const val ext_junit = "androidx.test.ext:junit:${Version.ext_junit}"
    const val espresso_core = "androidx.test.espresso:espresso-core:${Version.espresso_core}"

    const val recyclerview = "androidx.recyclerview:recyclerview:${Version.appcompat}"

    const val coordinatorlayout = "androidx.coordinatorlayout:coordinatorlayout:${Version.appcompat}"
    const val palette = "androidx.palette:palette:${Version.palette}"

    const val statusbarutil = "com.jaeger.statusbarutil:library:${Version.statusbarutil}"

    const val BaseRecyclerViewAdapterHelper = "com.github.CymChad:BaseRecyclerViewAdapterHelper:${Version.BaseRecyclerViewAdapterHelper}"
}

