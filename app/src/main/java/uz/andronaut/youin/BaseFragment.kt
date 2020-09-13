package uz.andronaut.gtg

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import uz.andronaut.youin.utils.LocaleUtils
import uz.andronaut.youin.utils.TinyDB

import java.util.prefs.AbstractPreferences

abstract class BaseFragment : Fragment(){

    lateinit var applicationPreferences: SharedPreferences
    lateinit var tinyDB: TinyDB

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = LayoutInflater.from(context).inflate(onLayoutSet(),container,false)

        if (context!=null) {
            applicationPreferences = context!!.getSharedPreferences(Constants.APP_PREFERENCES, Context.MODE_PRIVATE)
            tinyDB = TinyDB(activity)
        }

        onViewDidCreate(view,applicationPreferences, tinyDB, LocaleUtils.getPrefLangCode(context))

        return view
    }

    abstract fun onViewDidCreate(view : View,applicationPrefences : SharedPreferences, tinyDB: TinyDB,language : String)

    @LayoutRes
    abstract fun onLayoutSet() : Int

}