package com.intentsoft.mynotes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.intentsoft.mynotes.databinding.ActivityMainBinding
import com.intentsoft.mynotes.db.NoteDatabase
import com.intentsoft.mynotes.repository.NoteRepository
import com.intentsoft.mynotes.viewModel.NoteViewModel
import com.intentsoft.mynotes.viewModel.NoteViewModelProviderFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    lateinit var noteViewModel: NoteViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        setUpViewModel()
    }

    private fun setUpViewModel() {
        val noteRepository = NoteRepository(
            NoteDatabase(this)
        )
        val viewModelProviderFactory = NoteViewModelProviderFactory(
            application,
            noteRepository
        )

        noteViewModel = ViewModelProvider(
            this,
            viewModelProviderFactory
        ).get(NoteViewModel::class.java)
    }
}