package com.canerture.studentregistrationsystem

import android.os.Bundle
import android.view.*
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.canerture.studentregistrationsystem.databinding.FragmentAdministrationBinding

class AdministrationFragment : Fragment() {

    private var _binding: FragmentAdministrationBinding? = null
    private val binding get() = _binding!!

    private lateinit var db: Database

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        db = Database(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAdministrationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {

            btnAddFaculty.setOnClickListener {
                addDatabase(etFaculty, "Faculty")
            }

            btnAddDepartment.setOnClickListener {
                addDatabase(etDepartment, "Department")
            }

            btnAddLecturer.setOnClickListener {
                addDatabase(etLecturer, "Lecturer")
            }
        }
    }

    private fun addDatabase(et: EditText, toastSubject: String) {

        val data = et.text.toString()

        data.let {

            if (it.isNotEmpty()) {

                when (toastSubject) {
                    "Faculty" -> db.addFaculty(data)
                    "Department" -> db.addDepartment(data)
                    "Lecturer" -> db.addLecturer(data)
                }
            }
        }

        Toast.makeText(requireContext(), "$toastSubject successfully added!", Toast.LENGTH_SHORT)
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}