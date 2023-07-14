package app.junsu.androidserviceex

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import app.junsu.androidserviceex.databinding.ActivityMainBinding
import app.junsu.androidserviceex.music.MusicActivity

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(LayoutInflater.from(this)) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.btnPlayMusic.setOnClickListener {
            startActivity(Intent(this, MusicActivity::class.java))
        }
    }
}
