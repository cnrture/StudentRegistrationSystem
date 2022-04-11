package com.canerture.studentregistrationsystem

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.canerture.studentregistrationsystem.databinding.StudentsListDesignBinding

class RegisteredStudentsAdapter : RecyclerView.Adapter<RegisteredStudentsAdapter.StudentDesign>() {

    private val studentsList = ArrayList<StudentModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentDesign {
        val layoutInflater = LayoutInflater.from(parent.context)
        val studentsListDesignBinding =
            StudentsListDesignBinding.inflate(layoutInflater, parent, false)
        return StudentDesign(studentsListDesignBinding)
    }

    override fun onBindViewHolder(holder: StudentDesign, position: Int) {
        holder.bind(studentsList[position])
    }

    class StudentDesign(private var studentsListDesignBinding: StudentsListDesignBinding) :
        RecyclerView.ViewHolder(studentsListDesignBinding.root) {

        fun bind(student: StudentModel) {

            with(studentsListDesignBinding) {

                "${student.name} ${student.surname}".also { tvNameSurname.text = it }

                cvStudent.setOnClickListener {
                    val studentDetailNavigation =
                        RegisteredStudentsFragmentDirections.actionRegisteredStudentsFragmentToStudentDetailFragment(
                            student
                        )
                    it.findNavController().navigate(studentDetailNavigation)
                }
            }
        }

    }

    override fun getItemCount(): Int = studentsList.size

    fun updateList(list: List<StudentModel>) {
        studentsList.clear()
        studentsList.addAll(list)
        notifyItemRangeInserted(0, studentsList.size)
    }
}