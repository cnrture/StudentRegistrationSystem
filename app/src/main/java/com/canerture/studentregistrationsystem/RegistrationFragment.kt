package com.canerture.studentregistrationsystem

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.canerture.studentregistrationsystem.databinding.FragmentRegistrationBinding

class RegistrationFragment : Fragment() {

    private var _binding: FragmentRegistrationBinding? = null
    private val binding get() = _binding!!

    private lateinit var db: Database

    private var studentId = ""
    private var gender = ""
    private var name = ""
    private var surname = ""
    private var faculty = ""
    private var department = ""
    private var lecturer = ""

    private val faculties = ArrayList<String>()
    private val departments = ArrayList<String>()
    private val lecturers = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        db = Database(requireContext())

        faculties.addAll(db.getFaculties())
        departments.addAll(db.getDepartments())
        lecturers.addAll(db.getLecturers())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegistrationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setDropDownMenus()

        with(binding) {

            imgRegenerate.setOnClickListener {
                etStudentId.setText((1000000000..9999999999).random().toString())
            }

            rbMale.setOnClickListener {
                gender = rbMale.text.toString()
                rbFemale.isChecked = false
            }

            rbFemale.setOnClickListener {
                gender = rbFemale.text.toString()
                rbMale.isChecked = false
            }

            txtFaculty.setOnItemClickListener { _, _, position, _ ->
                faculty = faculties[position]
            }

            txtDepartment.setOnItemClickListener { _, _, position, _ ->
                department = departments[position]
            }

            txtLecturer.setOnItemClickListener { _, _, position, _ ->
                lecturer = lecturers[position]
            }

            btnRegister.setOnClickListener {
                if (checkFields()) {
                    registerSubmitDialog()
                } else {
                    Toast.makeText(requireContext(), "Please fill all fields!", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }

    private fun getStudentData(): StudentModel {

        with(binding) {

            studentId = etStudentId.text.toString()
            name = etName.text.toString()
            surname = etSurname.text.toString()
        }

        return StudentModel(0, studentId, gender, name, surname, faculty, department, lecturer)
    }

    private fun setDropDownMenus() {

        val facultiesAdapter =
            ArrayAdapter(requireContext(), R.layout.dropdown_menu_item, faculties)
        val departmentsAdapter =
            ArrayAdapter(requireContext(), R.layout.dropdown_menu_item, departments)
        val lecturersAdapter =
            ArrayAdapter(requireContext(), R.layout.dropdown_menu_item, lecturers)

        binding.txtFaculty.setAdapter(facultiesAdapter)
        binding.txtDepartment.setAdapter(departmentsAdapter)
        binding.txtLecturer.setAdapter(lecturersAdapter)
    }

    private fun checkFields(): Boolean {

        var isFilled = true

        with(binding) {

            studentId = etStudentId.text.toString()
            name = etName.text.toString()
            surname = etSurname.text.toString()

            if (studentId.isEmpty() || name.isEmpty() || surname.isEmpty() || faculty.isEmpty() || department.isEmpty() || lecturer.isEmpty()) {
                isFilled = false
            }

            if (rbMale.isChecked.not() && rbFemale.isChecked.not()) {
                isFilled = false
            }
        }

        return isFilled
    }

    private fun registerSubmitDialog() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Confirm Registration")
        builder.setMessage("Are you sure?")

        builder.setPositiveButton("Confirm") { dialog, _ ->
            db.addStudent(getStudentData())
            Toast.makeText(
                requireContext(),
                "Student added", Toast.LENGTH_SHORT
            ).show()
            dialog.dismiss()
        }

        builder.setNegativeButton("Cancel") { dialog, _ ->
            dialog.dismiss()
        }

        builder.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}