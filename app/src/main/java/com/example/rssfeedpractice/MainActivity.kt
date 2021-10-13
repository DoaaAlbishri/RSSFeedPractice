package com.example.rssfeedpractice

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : AppCompatActivity() {
    lateinit var listView : ListView
    var recentQuestions = mutableListOf<RecentQuestions>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        listView = findViewById(R.id.listView)
        FetchRecentQuestions().execute()
    }

    private inner class FetchRecentQuestions: AsyncTask<Void,Void,MutableList<RecentQuestions>>(){
        val parser = XMLParser()
        override fun doInBackground(vararg p0: Void?): MutableList<RecentQuestions> {
            val url = URL("https://stackoverflow.com/feeds")
            val urlConnection = url.openConnection() as HttpURLConnection
            recentQuestions = urlConnection.getInputStream()?.let {
                parser.parse(it)
            } as MutableList<RecentQuestions>
            return recentQuestions
        }

        override fun onPostExecute(result: MutableList<RecentQuestions>?) {
            super.onPostExecute(result)
            val adapter = ArrayAdapter(this@MainActivity,android.R.layout.simple_list_item_1,recentQuestions)
            listView.adapter=adapter
        }
    }
}