package com.example.lab1

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.io.File

class StorageActivity : AppCompatActivity() {

    companion object {
        private const val FILE_NAME = "history.txt"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_storage)

        val textViewContent = findViewById<TextView>(R.id.textViewStorageContent)
        val buttonClear = findViewById<Button>(R.id.buttonClearStorage)

        val buttonBack = findViewById<Button>(R.id.buttonBack)

        loadData(textViewContent)

        buttonClear.setOnClickListener {
            clearStorage(textViewContent)
        }

        buttonBack.setOnClickListener {
            finish()
        }
    }

    private fun loadData(textView: TextView) {
        val file = File(filesDir, FILE_NAME)

        if (file.exists() && file.length() > 0) {
            try {
                val content = openFileInput(FILE_NAME).bufferedReader().use { it.readText() }
                textView.text = content
            } catch (e: Exception) {
                e.printStackTrace()
                textView.text = "Помилка читання"
            }
        } else {
            textView.text = "Історія відсутня"
        }
    }

    private fun clearStorage(textView: TextView) {
        val file = File(filesDir, FILE_NAME)
        if (file.exists()) {
            file.delete()
            textView.text = "Історія відсутня"
            Toast.makeText(this, "Історія успішно очищена!", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Тут і так пусто!", Toast.LENGTH_SHORT).show()
        }
    }
}
