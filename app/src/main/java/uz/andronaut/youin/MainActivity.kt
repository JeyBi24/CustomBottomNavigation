package uz.andronaut.youin

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*
import uz.andronaut.gtg.BaseActivity
import uz.andronaut.youin.auth.AuthenticationAcitivity
import uz.andronaut.youin.utils.TinyDB
import uz.andronaut.youin.utils.bottomnavigation.MeowBottomNavigation

class MainActivity : BaseActivity() {

    override fun onActivityDidCreate(applicationPrefences: SharedPreferences, tinyDB: TinyDB) {
        checkAuthentication()


        bottomNavigation.add(MeowBottomNavigation.Model(1, R.drawable.calendarr))
        bottomNavigation.add(MeowBottomNavigation.Model(2, R.drawable.chat))
        bottomNavigation.add(MeowBottomNavigation.Model(3, R.drawable.ancient))


        bottomNavigation.show(1)

    }

    override fun onLayoutSet(): Int {
        return R.layout.activity_main


    }


    fun checkAuthentication(){
        if (FirebaseAuth.getInstance().currentUser==null){
            startActivity(Intent(this@MainActivity,AuthenticationAcitivity::class.java))

        }
    }


}
