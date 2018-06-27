package com.example.appmonsta

import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    private var searchButton: Button? = null
    private var packageNameView: TextView? = null
    private var resultView: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        packageNameView = findViewById(R.id.editText)
        resultView = findViewById(R.id.genres)
        searchButton = findViewById(R.id.button)

        searchButton?.setOnClickListener {
            GetGenresTask(resultView!!).execute(packageNameView?.text.toString())
        }
    }

    class GetGenresTask(view: TextView) : AsyncTask<String, Void, String>() {
        val resultView = view // TODO leak

        override fun doInBackground(vararg params: String?): String {
            val appMonsta = AppMonstaSearch()
            val packageName = params[0] as String
            return appMonsta.search(packageName)
        }

        override fun onPostExecute(result: String?) {
            resultView.text = result
        }
    }
}
