package com.example.wllpaper_app.Activity

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.example.wallhaven.Adapter.WallpaperAdapter
import com.example.wallhaven.Model.WallModel
import com.example.wallhaven.Retrofit.ApiClient
import com.example.wallhaven.Retrofit.ApiInterface
import com.example.wllpaper_app.R
import com.example.wllpaper_app.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private var wallModel: WallModel? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getSearchWallApi("all")
        binding.imgSearch.setOnClickListener {
            val searchData = binding.edtSearch.text.toString()
            getSearchWallApi(searchData)
        }


        binding.menu.setOnClickListener {

            binding.main.openDrawer(GravityCompat.START)

            binding.navigationDrawer.setNavigationItemSelectedListener {
                when (it.itemId) {


                    R.id.home -> {
                        binding.main.closeDrawer(GravityCompat.START)
                        Toast.makeText(this, "Home", Toast.LENGTH_SHORT).show()
                        true
                    }

                    R.id.setting -> {
                        binding.main.closeDrawer(GravityCompat.START)
                        Toast.makeText(this, "Settings", Toast.LENGTH_SHORT).show()
                        true
                    }


                    else -> {
                        false
                    }
                }
            }
        }

    }

    fun setRv() {
        val adapter = WallpaperAdapter(this@MainActivity, wallModel!!.hits)
        val lm = GridLayoutManager(this, 2)
        binding.wallrvdata.layoutManager = lm
        binding.wallrvdata.adapter = adapter


    }

    fun getSearchWallApi(q: String) {
        var apiInterface = ApiClient.getApiClient()?.create(ApiInterface::class.java)
        apiInterface?.searchWallPaper("41626448-dc177bdab9c7d049689854042", q, "flowers")
            ?.enqueue(object :
                Callback<WallModel> {
                override fun onResponse(call: Call<WallModel>, response: Response<WallModel>) {
                    if (response!!.code() == 200) {
                        wallModel = response.body()
                        setRv()
                    }

                }

                override fun onFailure(call: Call<WallModel>, t: Throwable) {
                    Log.e("TAG", "onFailure: ${t!!.message}")

                }

            })
    }

}

