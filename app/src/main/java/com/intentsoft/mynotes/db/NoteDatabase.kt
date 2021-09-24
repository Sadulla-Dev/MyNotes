package com.intentsoft.mynotes.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.intentsoft.mynotes.model.Note

@Database(entities = [Note::class], version = 1)
abstract class NoteDatabase: RoomDatabase() {

    abstract fun getNoteDao():NotesDao

    companion object {

        @Volatile
        private var instance: NoteDatabase? = null
        private val Lock = Any()

        operator fun invoke(context: Context) = instance?: synchronized(Lock){
            instance?:
            creatDatabase(context).also {
                instance = it
            }
        }

        private fun creatDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            NoteDatabase::class.java,
            "note_db"
        ).build()
    }
}