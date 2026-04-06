package com.example.lab1

import android.os.Bundle
import android.widget.*
import androidx.activity.ComponentActivity

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val radioGroupShapes = findViewById<RadioGroup>(R.id.radioGroupShapes)
        val checkArea = findViewById<CheckBox>(R.id.checkArea)
        val checkPerimeter = findViewById<CheckBox>(R.id.checkPerimeter)
        val buttonOk = findViewById<Button>(R.id.buttonOk)
        val textViewResult = findViewById<TextView>(R.id.textViewResult)

        buttonOk.setOnClickListener {
            val selectedShapeId = radioGroupShapes.checkedRadioButtonId
            val isAreaChecked = checkArea.isChecked
            val isPerimeterChecked = checkPerimeter.isChecked

            if (selectedShapeId == -1 || (!isAreaChecked && !isPerimeterChecked)) {
                Toast.makeText(this, "Будь ласка, завершіть введення всіх даних!", Toast.LENGTH_LONG).show()
            } else {
                val shapeRadioButton = findViewById<RadioButton>(selectedShapeId)
                val shapeName = shapeRadioButton.text.toString()

                val selectedParams = mutableListOf<String>()
                if (isAreaChecked) selectedParams.add("Площа")
                if (isPerimeterChecked) selectedParams.add("Периметр")
                
                val paramsText = selectedParams.joinToString(" та ")

                textViewResult.text = "Обрана фігура: $shapeName\nОбрані параметри: $paramsText"
            }
        }
    }
}
