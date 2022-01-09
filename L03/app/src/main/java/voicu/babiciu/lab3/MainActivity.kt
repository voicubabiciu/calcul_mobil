package voicu.babiciu.lab3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.AdapterView
import android.widget.Toast
import voicu.babiciu.lab3.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private val pin: String = "1234"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.pinGrid.adapter = GridAdapter(
            this, listOf(
                "1", "2", "3", "4",
                "5", "6", "7", "8", "9"
            )
        )
        binding.pinGrid.onItemClickListener =
            AdapterView.OnItemClickListener { _, _, position, _ ->
                binding.pinField.editText?.let {


                    binding.pinField.error = null
                    if (it.text.length < 4) {
                        val content =
                            it.text.toString() + (position + 1).toString()
                        it.setText(content)
                    }
                    if (it.text.length == 4) {

                        if (it.text.toString() == pin) {
                            val intent = Intent(
                                this,
                                BookSearchActivity().javaClass
                            )
                            startActivity(intent)
                        } else {
                            binding.pinField.error = "Invalid pin"
                            it.setText("")
                        }
                    }


                }
            }

    }
}