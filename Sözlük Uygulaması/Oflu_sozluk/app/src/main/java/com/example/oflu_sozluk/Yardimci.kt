package com.example.oflu_sozluk

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.TypedValue
import android.widget.LinearLayout
import android.widget.TextView
import java.lang.Exception

class Yardim(val ct:Context):SQLiteOpenHelper(ct,"ibo",null,1){
    private val ingilizce= arrayOf("yes","no","cat","dog","home","hi")
    private val turkce=arrayOf("evet","hayir","kedi","kopek","ev","merhaba")
    override fun onCreate(db: SQLiteDatabase?) {
        db!!.execSQL("create table if not exists sozluk(id integer primary key autoincrement,kelime text,karsilik text);")
        for (i in 0 until ingilizce.size){
            val ing=ingilizce[i]
            val turk=turkce[i]
            val sorgu="insert into sozluk(kelime,karsilik) values('$ing','$turk');"
            db!!.execSQL(sorgu)
        }
    }
    fun kelimeleriDoldur(liste:LinearLayout){
        val kursor=this.readableDatabase.rawQuery("select kelime,karsilik from sozluk;", null)
        kursor.moveToFirst()
        while(!kursor.isAfterLast){
            val yazi=TextView(ct)
            yazi.setTextSize(TypedValue.COMPLEX_UNIT_SP,24f)
            val tr=kursor.getString(kursor.getColumnIndex("kelime"))
            val eng=kursor.getString(kursor.getColumnIndex("karsilik"))
            yazi.text="$eng : $tr"
            liste.addView(yazi)
            kursor.moveToNext()
        }
        kursor.close()
    }
    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db!!.execSQL("drop table if exists sozluk;")
    }
}