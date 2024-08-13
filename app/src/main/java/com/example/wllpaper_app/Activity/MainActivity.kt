package com.example.wllpaper_app.Activity

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
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
        binding = ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

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


    private fun replaceFragment(fragment: Fragment) {

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.Flayout, fragment)
            .commit()
    }

    fun setRv() {
        val adapter = WallpaperAdapter(this@MainActivity, wallModel!!.hits)
        val lm = GridLayoutManager(this@MainActivity, 2)
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

