package com.example.lab1

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels

class InputFragment: Fragment() {
    private val sharedViewModel: SharedViewModel by activityViewModels()

    companion object {
        private const val FILE_NAME = "history.txt"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_input, container, false)

        val radioGroupShapes = view.findViewById<RadioGroup>(R.id.radioGroupShapes)
        val checkArea = view.findViewById<CheckBox>(R.id.checkArea)
        val checkPerimeter = view.findViewById<CheckBox>(R.id.checkPerimeter)
        val buttonOk = view.findViewById<Button>(R.id.buttonOk)

        val buttonOpenStorage = view.findViewById<Button>(R.id.buttonOpenStorage)

        buttonOk.setOnClickListener {
            val selectedShapeId = radioGroupShapes.checkedRadioButtonId
            val isAreaChecked = checkArea.isChecked
            val isPerimeterChecked = checkPerimeter.isChecked

            if (selectedShapeId == -1 || (!isAreaChecked && !isPerimeterChecked)) {
                Toast.makeText(requireContext(), "Будь ласка, завершіть введення всіх даних!", Toast.LENGTH_LONG).show()
            } else {
                val shapeRadioButton = view.findViewById<RadioButton>(selectedShapeId)
                val shapeName = shapeRadioButton.text.toString()
                val selectedParams = mutableListOf<String>()

                if (isAreaChecked) selectedParams.add("Площа")
                if (isPerimeterChecked) selectedParams.add("Периметр")

                val paramsText = selectedParams.joinToString(" та ")

                val resultString = "Обрана фігура: $shapeName\nОбрані параметри: $paramsText"
                sharedViewModel.resultData = resultString

                saveDataToFile(resultString)
                Toast.makeText(requireContext(), "Дані успішно збережено!", Toast.LENGTH_SHORT).show()

                radioGroupShapes.clearCheck()
                checkArea.isChecked = false
                checkPerimeter.isChecked = false

                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, ResultFragment())
                    .addToBackStack(null)
                    .commit()
            }
        }

        buttonOpenStorage.setOnClickListener {
            val intent = Intent(requireContext(), StorageActivity::class.java)
            startActivity(intent)
        }

        return view
    }

    private fun saveDataToFile(data: String) {
        try {
            val dataToWrite = "$data\n" + "⎯".repeat(18) + "\n"

            requireContext().openFileOutput(FILE_NAME, Context.MODE_APPEND).use {
                it.write(dataToWrite.toByteArray())
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(requireContext(), "Помилка при збереженні", Toast.LENGTH_SHORT).show()
        }
    }
}