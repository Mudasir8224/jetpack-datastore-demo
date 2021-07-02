package com.example.datastoredemo

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.preferencesKey
import androidx.datastore.preferences.createDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MyPreference(context:Context) {

    private val applicationContext = context.applicationContext
    private val dataStore: DataStore<Preferences>

    init {
        dataStore = applicationContext.createDataStore(name = "app_preferences")
    }

    val notes: Flow<String?>
        get() = dataStore.data.map { preferences ->
            preferences[KEY_NOTES]
        }

    val notesId: Flow<Long?>
        get() = dataStore.data.map { preferences ->
            preferences[KEY_NOTES_ID]
        }

    suspend fun saveNotes(notes:String){
        dataStore.edit { preferences ->
            preferences[KEY_NOTES] = notes
        }
    }

    suspend fun saveNotesId(notesId: Long){
        dataStore.edit { preferences ->
            preferences[KEY_NOTES_ID] = notesId
        }
    }

    companion object {
        val KEY_NOTES = preferencesKey<String>("key_notes")
        val KEY_NOTES_ID = preferencesKey<Long>("key_notes_id")
    }

}