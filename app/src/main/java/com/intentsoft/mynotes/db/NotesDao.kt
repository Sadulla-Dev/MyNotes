package com.intentsoft.mynotes.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.intentsoft.mynotes.model.Note

@Dao
interface NotesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addNote(note: Note)

    @Update
    suspend fun updateNote(note: Note)

    @Delete
    suspend fun deleteNote(note: Note)

    @Query("SELECT * FROM notes ORDER BY id DESC")
    fun getAllNotes(): LiveData<List<Note>>

    @Query("SELECT * FROM NOTES WHERE nateTitle LIKE :query OR noteBody LIKE:query")
    fun searchNote(query: String?): LiveData<List<Note>>
}