package org.bandev.buddhaquotes.datastore.imagePref

import android.util.Log
import androidx.datastore.core.DataStore
import java.io.IOException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import org.bandev.buddhaquotes.imagepref.ImagePref
import javax.inject.Inject

class ImagePrefRepository @Inject constructor(private val store: DataStore<ImagePref>) {
    val settings: Flow<ImagePref> = store.data
        .catch { e ->
            if (e is IOException) {
                Log.e("TAG", "Error reading sort order preferences: $e")
                emit(ImagePref.getDefaultInstance())
            } else {
                throw e
            }
        }

    suspend fun setImage(image: Int) {
        store.updateData { current ->
            current.toBuilder().setImage(image).build()
        }
    }
}
