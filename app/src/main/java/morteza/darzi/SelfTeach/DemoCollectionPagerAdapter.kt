package morteza.darzi.SelfTeach

import BL.Book
import BL.Teacher
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class DemoCollectionPagerAdapter(fm: FragmentManager, private val teacher: Teacher, private val freeBooks: List<Book>) : FragmentPagerAdapter(fm) {
    private var t = listOf<String>()

    init {
        val t3 = listOf("مطالعه آزاد", "ترم")
        val t2 = listOf("ترم", "مطالعه آزاد")
        val t1 = listOf("عملکرد ترم", "کتاب های ترم", "مطالعه آزاد")
        if (teacher.IsBooksSet()!!)
            t = t1
        else if ((freeBooks.size > 0) and teacher.IsTermSet()!!.not())
            t = t3
        else
            t = t2

    }


    override fun getItem(i: Int): Fragment {

        when (i) {
             0 -> {
                 when {
                     teacher.IsTermSet()!! -> {
                         val termMain = TermMain()
                         termMain.GiveTeacher(teacher)
                         return termMain
                     }
                     freeBooks.size > 0 -> {
                         val fbookMain = FreeBookMain()
                         fbookMain.GiveBooks(freeBooks)
                         return fbookMain
                     }
                     else -> {
                         val termMain = TermMain()
                         termMain.GiveTeacher(teacher)
                         return termMain
                     }
                 }
             }
             1 -> {
                 when {
                     teacher.IsBooksSet()!! -> {
                         val bookMain = BookMain()
                         bookMain.GiveTeacher(teacher)
                         return bookMain
                     }
                     teacher.IsTermSet()!! -> {
                         val fbookMain = FreeBookMain()
                         fbookMain.GiveBooks(freeBooks)
                         return fbookMain
                     }
                     freeBooks.size > 0 -> {
                         val termMain = TermMain()
                         termMain.GiveTeacher(teacher)
                         return termMain
                     }
                     else -> {
                         val fbookMain = FreeBookMain()
                         fbookMain.GiveBooks(freeBooks)
                         return fbookMain
                     }
                 }
             }
             2 -> {
                 val fbookMain = FreeBookMain()
                 fbookMain.GiveBooks(freeBooks)
                 return fbookMain
             }
            else -> {
                val fbookMain = FreeBookMain()
                fbookMain.GiveBooks(freeBooks)
                return fbookMain
            }
        }

    }

    override fun getCount(): Int {
        return if (teacher.IsBooksSet()!!.not())
            2
        else
            3
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return t[position]
    }
}

