package com.intentsoft.mynotes.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.intentsoft.mynotes.MainActivity
import com.intentsoft.mynotes.R
import com.intentsoft.mynotes.databinding.FragmentUpdateNoteBinding
import com.intentsoft.mynotes.model.Note
import com.intentsoft.mynotes.toast
import com.intentsoft.mynotes.viewModel.NoteViewModel
import java.util.*


class UpdateNoteFragment : Fragment() {

    private var _binding: FragmentUpdateNoteBinding? = null
    private val binding get() = _binding!!

    private val args: UpdateNoteFragmentArgs by navArgs()
    private lateinit var currentNote: Note
    private lateinit var noteViewModel: NoteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentUpdateNoteBinding.inflate(
            inflater,
            container,
            false
        )
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        noteViewModel = (activity as MainActivity).noteViewModel
        currentNote = args.note!!

        binding.etNoteBodyUpdate.setText(currentNote.noteBody)
        binding.etNoteTitleUpdate.setText(currentNote.nateTitle)
        binding.tvTimeUpDate.text = currentNote.noteTime

        binding.fabDone.setOnClickListener {
            val date = Calendar.getInstance()
            val year = date.get(Calendar.YEAR)
            val month = date.get(Calendar.MONTH)
            val day = date.get(Calendar.DATE)
            val time13 = date.get(Calendar.HOUR)
            val time1 = date.get(Calendar.MINUTE)
            val s = "$year.$month.$day"


            val title = binding.etNoteTitleUpdate.text.toString().trim()
            val body = binding.etNoteBodyUpdate.text.toString().trim()
            val time = s.trim()

            if (title.isNotEmpty()) {
                val note = Note(currentNote.id, title, body,time)
                noteViewModel.updateNote(note)

                view.findNavController().navigate(R.id.action_updateNoteFragment_to_homeFragment)

            } else {
                activity?.toast("Iltimos, eslatma sarlavhasini kiriting")
            }
        }
    }

    private fun deleteNote() {
        AlertDialog.Builder(activity).apply {
            setTitle("Qaydni o'chirish")
            setMessage("Haqiqatan ham bu eslatmani butunlay o‘chirib tashlamoqchimisiz?")
            setPositiveButton("O'chirish") { _, _ ->
                noteViewModel.deleteNote(currentNote)
                view?.findNavController()?.navigate(
                    R.id.action_updateNoteFragment_to_homeFragment
                )
            }
            setNegativeButton("Bekor qilish", null)
        }.create().show()

    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.update_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_delete -> {
                deleteNote()
            }
        }

        return super.onOptionsItemSelected(item)
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}