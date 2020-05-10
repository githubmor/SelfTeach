package core

class TodaySummery(private val performance: TermPerformance) : ISummery {
    private val pageShouldReadTo100 = performance.pageReadTo100Percent
    private val pageReaded = performance.pageReaded
    private val pageShouldReadTillToday = performance.shouldReadTillToday
    private val dayPas = performance.pasDay
    private val avgPagePerDay = performance.avgPageEveryday
    override val title: String
        get() = "تعداد صفحه خواندن امروز"


    val s1 =
            """
        تعداد صفحه ای که باید امروز خوانده شود :
        تعداد صفحه ای که باید تا امروز خوانده میشد - تعداد صفحه ای که خوانده شد
        با توجه به اینکه بطور متوسط در طول ترم باید روزانه $avgPagePerDay  صفحه خوانده میشد ( تعداد کل صفحات / تعداد کل روز ) و اینکه $dayPas  روز از ترم گذشته ، باید تا امروز $pageShouldReadTillToday  صفحه را میخواندید . همانطور که میدانید تا بحال $pageReaded  صفحه را خواندید . پس در نتیجه نیاز خواهد بود که $pageShouldReadTo100  صفحه دیگر را بخوانید تا بتوانید طبق برنامه پیش رفته باشید
        این عدد نشان میدهد چند صفحه دیگر باید امرو بخوانید تا عملکرد شما در این روز 100 درصد شود
    """.trimIndent()

    override fun getSummery(): String {
        return s1
    }
}