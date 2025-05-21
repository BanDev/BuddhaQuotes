package org.bandev.buddhaquotes.datastore.imagePref

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.google.protobuf.InvalidProtocolBufferException
import org.bandev.buddhaquotes.imagepref.ImagePref
import java.io.InputStream
import java.io.OutputStream

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
