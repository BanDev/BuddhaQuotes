package org.bandev.buddhaquotes.datastore.imagePref

import android.content.Context
import androidx.datastore.core.CorruptionException
import androidx.datastore.core.DataStore
import androidx.datastore.core.Serializer
import androidx.datastore.dataStore
import com.google.protobuf.InvalidProtocolBufferException
import java.io.InputStream
import java.io.OutputStream
import org.bandev.buddhaquotes.imagepref.ImagePref

object ImagePrefSerializer : Serializer<ImagePref> {
    override val defaultValue: ImagePref = ImagePref.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): ImagePref {
        try {
            return ImagePref.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto.", exception)
        }
    }

    override suspend fun writeTo(t: ImagePref, output: OutputStream) = t.writeTo(output)
}

val Context.imagePrefStore: DataStore<ImagePref> by dataStore(
    fileName = "ImagePref.pb",
    serializer = ImagePrefSerializer,
)
