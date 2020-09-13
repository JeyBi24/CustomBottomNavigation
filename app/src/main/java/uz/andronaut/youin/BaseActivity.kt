package uz.andronaut.gtg

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import uz.andronaut.youin.utils.LocaleUtils
import uz.andronaut.youin.utils.TinyDB


abstract class BaseActivity : AppCompatActivity(){
    lateinit var applicationPreferences: SharedPreferences
    lateinit var tinyDB: TinyDB

    override fun onCreate(savedInstanceState: Bundle?) {

//        LocaleUtils.updateConfiguration(this,LocaleUtils.getPrefLangCode(this))
        super.onCreate(savedInstanceState)
        setContentView(onLayoutSet())
        applicationPreferences = getSharedPreferences(Constants.APP_PREFERENCES, Context.MODE_PRIVATE)
        tinyDB = TinyDB(this)
        onActivityDidCreate(applicationPreferences,tinyDB)
    }


    abstract fun onActivityDidCreate(applicationPrefences : SharedPreferences, tinyDB: TinyDB)

    @LayoutRes
    abstract fun onLayoutSet() : Int


}