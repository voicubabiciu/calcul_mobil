package voicu.babiciu.lab04

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import voicu.babiciu.lab04.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.itemsRv.layoutManager = LinearLayoutManager(this)
        binding.itemsRv.adapter = ItemsAdapter(
            generateItems()
        ) {
            val intent = Intent(this, ItemDetailsActivity().javaClass)
            intent.putExtra("ITEM", it)
            startActivity(intent)
        }

    }

    private fun generateItems(count: Int = 100):
            List<ItemModel> = List(count) {
        ItemModel("title ${it + 1}", "description ${it + 1}")


    }
}