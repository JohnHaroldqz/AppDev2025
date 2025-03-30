package ph.edu.cksc.college.appdev.appdev2025.service

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.dataObjects
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.tasks.await
import ph.edu.cksc.college.appdev.appdev2025.data.DiaryEntry

data class User(
    val id: String = "",
    val isAnonymous: Boolean = true
)

class StorageService(
    val auth: FirebaseAuth,
    val firestore: FirebaseFirestore
) {
    private val collection get() = firestore.collection(DIARYENTRY_COLLECTION)
        .whereEqualTo(USER_ID_FIELD, auth.currentUser?.uid)
    val currentUser: Flow<User>
        get() = callbackFlow {
            val listener =
                FirebaseAuth.AuthStateListener { auth ->
                    this.trySend(auth.currentUser?.let { User(it.uid, false) } ?: User())
                }
            auth.addAuthStateListener(listener)
            awaitClose { auth.removeAuthStateListener(listener) }
        }

    @OptIn(ExperimentalCoroutinesApi::class)
    val entries: Flow<List<DiaryEntry>>
        get() =
            currentUser.flatMapLatest { user ->
                firestore
                    .collection(DIARYENTRY_COLLECTION)
                    .whereEqualTo(USER_ID_FIELD, user.id)
                    .orderBy(DATETIME_FIELD, Query.Direction.DESCENDING)
                    .dataObjects()
            }

    @OptIn(ExperimentalCoroutinesApi::class)
    fun getFilteredEntries(dateFilter: String): Flow<List<DiaryEntry>> {
        return currentUser.flatMapLatest { user ->
            firestore
                .collection(DIARYENTRY_COLLECTION)
                .whereEqualTo(USER_ID_FIELD, user.id)
                .whereGreaterThanOrEqualTo(DATETIME_FIELD, dateFilter)
                .whereLessThanOrEqualTo(DATETIME_FIELD, "${dateFilter}\uf8ff")
                .orderBy(DATETIME_FIELD, Query.Direction.DESCENDING)
                .dataObjects()
        }
    }

    /*suspend fun getDiaryEntry(diaryEntryId: String): DiaryEntry? =
        firestore.collection(DIARYENTRY_COLLECTION).document(diaryEntryId).get().await().toObject()*/

    suspend fun save(diaryEntry: DiaryEntry): String {
        val updatedDiaryEntry = diaryEntry.copy(userId = auth.currentUser?.uid ?: "")
        firestore.collection(DIARYENTRY_COLLECTION).add(updatedDiaryEntry)//.await().id
        return "";
    }

    suspend fun update(diaryEntry: DiaryEntry) {
        firestore.collection(DIARYENTRY_COLLECTION).document(diaryEntry.id).set(diaryEntry)//.await()
    }

    suspend fun delete(diaryEntryId: String) {
        firestore.collection(DIARYENTRY_COLLECTION).document(diaryEntryId).delete().await()
    }

    companion object {
        private const val USER_ID_FIELD = "userId"
        private const val MOOD_FIELD = "mood"
        private const val TITLE_FIELD = "title"
        private const val CONTENT_FIELD = "content"
        private const val DATETIME_FIELD = "dateTime"
        private const val CREATED_AT_FIELD = "createdAt"
        private const val DIARYENTRY_COLLECTION = "entries"
        private const val SAVE_DIARYENTRY_TRACE = "saveDiaryEntry"
        private const val UPDATE_DIARYENTRY_TRACE = "updateDiaryEntry"
    }
}