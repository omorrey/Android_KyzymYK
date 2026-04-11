package com.example.lab1

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels

class InputFragment : Fragment() {

    private val sharedViewModel: SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_input, container, false)

        val radioGroupShapes = view.findViewById<RadioGroup>(R.id.radioGroupShapes)
        val checkArea = view.findViewById<CheckBox>(R.id.checkArea)
        val checkPerimeter = view.findViewById<CheckBox>(R.id.checkPerimeter)
        val buttonOk = view.findViewById<Button>(R.id.buttonOk)

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

                sharedViewModel.resultData = "Обрана фігура: $shapeName\nОбрані параметри: $paramsText"

                radioGroupShapes.clearCheck()
                checkArea.isChecked = false
                checkPerimeter.isChecked = false

                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, ResultFragment())
                    .addToBackStack(null)
                    .commit()
            }
        }

        return view
    }
}