package com.canerture.studentregistrationsystem

import android.os.Bundle
import android.view.*
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.canerture.studentregistrationsystem.databinding.FragmentStudentDetailBinding

class StudentDetailFragment : Fragment() {

    private var _binding: FragmentStudentDetailBinding? = null
    private val binding get() = _binding!!

    private lateinit var db: Database

    private var dbId = 0
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

    private val args: StudentDetailFragmentArgs by navArgs()

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
        _binding = FragmentStudentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fillStudentData()

        with(binding) {

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

            btnCancel.setOnClickListener {
                it.findNavController().popBackStack()
            }

            btnUpdate.setOnClickListener {
                studentId = etStudentId.text.toString()
                name = etStudentName.text.toString()
                surname = etStudentSurname.text.toString()
                db.updateStudent(
                    StudentModel(
                        dbId,
                        studentId,
                        gender,
                        name,
                        surname,
                        faculty,
                        department,
                        lecturer
                    )
                )
            }
        }
    }

    private fun fillStudentData() {

        args.selectedStudent.let {
            dbId = it.id
            studentId = it.studentId
            name = it.name
            surname = it.surname
            gender = it.gender
            faculty = it.faculty
            department = it.department
            lecturer = it.lecturer
        }

        with(binding) {

            etStudentId.setText(studentId)
            etStudentName.setText(name)
            etStudentSurname.setText(surname)

            if (gender == "Male") rbMale.isChecked = true else rbFemale.isChecked = true

            txtFaculty.setText(faculty)
            txtDepartment.setText(department)
            txtLecturer.setText(lecturer)
        }

        setDropDownMenus()
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}