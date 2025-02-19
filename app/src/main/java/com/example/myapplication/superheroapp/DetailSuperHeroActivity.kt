package com.example.myapplication.superheroapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.View
import androidx.core.view.isVisible
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityDetailSuperHeroBinding
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.math.roundToInt

class DetailSuperHeroActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_ID = "extra_id"
    }
    private lateinit var binding: ActivityDetailSuperHeroBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailSuperHeroBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val id = intent.getStringExtra(EXTRA_ID).orEmpty()
        getSuperheroInfo(id)
    }

    private fun getSuperheroInfo(id: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val superHeroDetail = getRetrofit().create(ApiService::class.java).getSuperheroDetail(id)
            if (superHeroDetail.body() != null) {
                runOnUiThread {
                    createUI(superHeroDetail.body()!!)
                }
            }
        }
    }

    private fun createUI(superhero: SuperHeroDetailResponse) {
        Picasso.get().load(superhero.image.url).into(binding.ivSuperhero)
        binding.tvSuperHeroName.text = superhero.name
        binding.tvSuperHeroRealName.text = superhero.biography.fullName
        binding.tvSuperHeroPublisher.text = superhero.biography.publisher
        prepareStats(superhero.powerStats)
    }

    private fun prepareStats(powerStats: PowerStatsResponse) {
        with(binding) {
            viewCombat.updateHeight(powerStats.combat)
            viewDurability.updateHeight(powerStats.durability)
            viewPower.updateHeight(powerStats.power)
            viewIntelligence.updateHeight(powerStats.intelligence)
            viewSpeed.updateHeight(powerStats.speed)
            viewStrength.updateHeight(powerStats.strength)
        }
    }

    private fun View.updateHeight(stat: String) {
        val params = this.layoutParams
        params.height = pxToDp(stat.toFloat())
        this.layoutParams = params
    }

    private fun pxToDp(px: Float): Int {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, px, resources.displayMetrics).roundToInt()
    }

    private fun getRetrofit(): Retrofit {
        return Retrofit
            .Builder()
            .baseUrl("https://superheroapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}