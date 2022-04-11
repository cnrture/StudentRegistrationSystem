package com.canerture.studentregistrationsystem

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.canerture.studentregistrationsystem.databinding.FragmentRegisteredStudentsBinding

class RegisteredStudentsFragment : Fragment() {

    private var _binding: FragmentRegisteredStudentsBinding? = null
    private val binding get() = _binding!!

    private lateinit var db: Database

    private val registeredStudentsAdapter by lazy { RegisteredStudentsAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisteredStudentsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        db = Database(requireContext())

        registeredStudentsAdapter.updateList(db.getStudents())

        with(binding) {

            rvRegisteredStudents.apply {

                layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                setHasFixedSize(true)
                adapter = registeredStudentsAdapter
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}