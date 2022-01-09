package voicu.babiciu.lab04

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import voicu.babiciu.lab04.databinding.ActivityItemDetailsBinding

class ItemDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityItemDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityItemDetailsBinding.inflate(layoutInflater)

        setContentView(binding.root)
        if (intent.hasExtra("ITEM")) {
            val item = intent.getSerializableExtra("ITEM") as ItemModel
            binding.titleTv.text = item.title
            binding.detailsTv.text = item.details
        }

    }
}