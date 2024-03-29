package com.example.jourlyapp.model.journal.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.jourlyapp.model.journal.entities.JournalEntry
import com.example.jourlyapp.model.journal.entities.QuestionAnswerPair
import kotlinx.coroutines.flow.Flow

/**
 * Interface for simple access of journal entries in local DB.
 * Also see: https://developer.android.com/training/data-storage/room/accessing-data
 */
@Dao
interface JournalDao {
    @Insert
    suspend fun insertEntry(entry: JournalEntry)

    @Query("SELECT * FROM journal_entry ORDER BY date DESC")
    fun getEntries(): Flow<List<JournalEntry>>

    @Query("DELETE FROM journal_entry WHERE id = :journalEntryId")
    suspend fun deleteEntryById(journalEntryId: Int)

    @Query("DELETE FROM journal_entry")
    suspend fun deleteAllEntries()

    @Insert
    suspend fun insertQuestionAnswerPair(questionAnswerPair: QuestionAnswerPair)

    @Query(
        "SELECT qa.id, qa.journal_entry_id, qa.question, qa.answer FROM journal_entry " +
                "JOIN question_answer_pair as qa ON journal_entry.id = qa.journal_entry_id AND journal_entry_id = :journalEntryId"
    )
    fun getQuestionnaireByEntryId(journalEntryId: Int): List<QuestionAnswerPair>

    @Query("DELETE FROM question_answer_pair")
    suspend fun deleteAllQuestionAnswerPairs()
}
