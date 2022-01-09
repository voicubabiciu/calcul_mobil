package voicu.babiciu.lab08

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.*
import voicu.babiciu.lab08.databinding.ActivityMainBinding
import java.io.IOException
import org.json.JSONObject




class MainActivity : AppCompatActivity() {

    private val scope = CoroutineScope(Dispatchers.IO)
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.button.setOnClickListener {
            scope.launch {
                Log.d("###", "starting test ...")
                val client = OkHttpClient()
                val mediaType =
                    MediaType.parse("application/x-www-form-urlencoded")
                val body: RequestBody = RequestBody.create(
                    mediaType,
                    "text=${binding.editText.text}&tl=ro&sl=en"
                )

                val request = Request.Builder()
                    .url("https://google-translate20.p.rapidapi.com/translate")
                    .post(body)
                    .addHeader(
                        "content-type",
                        "application/x-www-form-urlencoded"
                    )
                    .addHeader(
                        "x-rapidapi-host",
                        "google-translate20.p.rapidapi.com"
                    )
                    .addHeader(
                        "x-rapidapi-key",
                        "cf26dd3c73mshdd5e0ce40dc73f9p173d45jsn8d4ef6944414"
                    )
                    .build()
                try {
                    val response = client.newCall(request).execute()
                    val r = response.body()?.string()
                    val jsonObject = JSONObject(r!!)
                    withContext(Dispatchers.Main) {
                        binding.textView.text =
                            jsonObject.getJSONObject("data")
                                .getString("translation")
                    }
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
    }
}