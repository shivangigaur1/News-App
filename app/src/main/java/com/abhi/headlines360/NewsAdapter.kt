package com.abhi.headlines360

import android.content.Context
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import java.util.*

class NewsAdapter(
    private var newsList: List<Article>,
    private val onItemClick: (String) -> Unit,
    private val context: Context
) : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>(), TextToSpeech.OnInitListener {

    private var textToSpeech: TextToSpeech? = null
    private var currentHolder: NewsViewHolder? = null  // ✅ Moved here

    init {
        textToSpeech = TextToSpeech(context, this)

        textToSpeech?.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
            override fun onStart(utteranceId: String?) {
                (context as? AppCompatActivity)?.runOnUiThread {
                    currentHolder?.buttonListen?.text = "Stop"
                }
            }

            override fun onDone(utteranceId: String?) {
                (context as? AppCompatActivity)?.runOnUiThread {
                    currentHolder?.buttonListen?.text = "Listen"
                }
            }

            override fun onError(utteranceId: String?) {
                (context as? AppCompatActivity)?.runOnUiThread {
                    currentHolder?.buttonListen?.text = "Listen"
                }
            }
        })
    }

    class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageViewNews)
        val titleTextView: TextView = itemView.findViewById(R.id.textViewTitle)
        val readMoreTextView: TextView = itemView.findViewById(R.id.textViewReadMore)
        val textViewDescription: TextView = itemView.findViewById(R.id.textViewDescription)
        val buttonListen: Button = itemView.findViewById(R.id.buttonListen)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.news_item, parent, false)
        return NewsViewHolder(view)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val article = newsList[position]

        Glide.with(holder.itemView.context)
            .load(article.urlToImage)
            .placeholder(R.drawable.ic_launcher_background)
            .into(holder.imageView)

        holder.titleTextView.text = article.title
        holder.textViewDescription.text = article.description ?: "No description available"

        holder.readMoreTextView.setOnClickListener {
            onItemClick(article.url)
        }

        // Disable TTS button if battery is low
        holder.buttonListen.isEnabled = !PowerHelper.isBatteryLow(context)
        holder.buttonListen.setOnClickListener {
            if (!PowerHelper.isBatteryLow(context)) {
                if (textToSpeech?.isSpeaking == true) {
                    textToSpeech?.stop()
                    holder.buttonListen.text = "Listen"
                } else {
                    speakOut(article.title, article.description, holder)
                }
            } else {
                Toast.makeText(context, "TTS disabled in power saving mode", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun getItemCount() = newsList.size

    fun updateNews(newNewsList: List<Article>) {
        newsList = newNewsList
        notifyDataSetChanged()
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val result = textToSpeech?.setLanguage(Locale.US)

            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "Language not supported")
            } else {
                Log.d("TTS", "TextToSpeech initialized successfully")
            }
        } else {
            Log.e("TTS", "TTS Initialization failed")
        }
    }

    private fun speakOut(title: String?, description: String?, holder: NewsViewHolder) {
        currentHolder = holder  // ✅ Save reference to update UI on TTS callbacks
        val utteranceId = UUID.randomUUID().toString()
        val textToSpeak = "${title ?: ""}. ${description ?: "No description available"}"
        textToSpeech?.speak(textToSpeak, TextToSpeech.QUEUE_FLUSH, null, utteranceId)
    }

    fun shutdownTTS() {
        textToSpeech?.stop()
        textToSpeech?.shutdown()
    }
}
