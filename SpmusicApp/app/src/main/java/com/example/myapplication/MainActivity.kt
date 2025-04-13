package com.example.myapplication

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.math.log

class MainActivity : AppCompatActivity() {

    lateinit var myrecyclerview: RecyclerView
    lateinit var myAdapter: MyAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        myrecyclerview= findViewById(R.id.reclyclerview)

        val retrofitBuilder = Retrofit.Builder()
            .baseUrl("https://deezerdevs-deezer.p.rapidapi.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiInterface::class.java)

        val retrofitCall = retrofitBuilder.getData("eminem")
        retrofitCall.enqueue(
            object : retrofit2.Callback<MyData?> {
                override fun onResponse(p0: Call<MyData?>, p1: Response<MyData?>) {
                    val responseBody = p1.body()?.data
//                    val textview=findViewById<TextView>(R.id.reclyclerview)
//                    textview.text=responseBody.toString()
                    myAdapter= MyAdapter(this@MainActivity, responseBody!!)
                    myAdapter.notifyDataSetChanged()
                    myrecyclerview.adapter= myAdapter
                    myrecyclerview.layoutManager= LinearLayoutManager(this@MainActivity)
                    Log.d("onResponse", "onResponse:" + p1.body())
                }
                override fun onFailure(p0: Call<MyData?>, p1: Throwable) {
                    Log.d("onFailure", "onFailure:" + p1.message)
                }
            })
    }
}