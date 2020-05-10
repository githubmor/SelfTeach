package core

class AvregPerDaySummery(private val performance: TermPerformance) : ISummery {
    override val title: String
        get() = "متوسط صفحه/روز باقیمانده"

    private val dayRemind = performance.dayRemind
    private val pageRemind = performance.pageRemind
    val s1 =
            """
        میانگین صفحه در روزی که باید خوانده شود :
        تعداد صفحه باقیمانده / تعداد روز باقیمانده
        با توجه به اینکه تابحال تعداد $pageRemind  صفحه از کتابها باقیمانده و از امروز $dayRemind  روز تا پایان ترم داریم . میانگین  صفحه در روز باید تا پایان ترم در نظر گرفته شود
        این عدد نشان دهنده شتاب مورد نیاز در خواندن تا پایان ترم است و اگر به نظرتان تعداد بیشتری نسبت به تعداد صفحه ای است که شما میتوانید بخواندی باید حتما یک فوق برنامه در نظر بگیرید
    """.trimIndent()

    override fun getSummery(): String {
        return s1
    }
}