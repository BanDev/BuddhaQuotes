package org.bandev.buddhaquotes.datastore.settings

import android.util.Log
import androidx.datastore.core.Serializer
import com.google.protobuf.InvalidProtocolBufferException
import org.bandev.buddhaquotes.settings.Settings
import java.io.InputStream
import java.io.OutputStream

object SettingsSerializer : Serializer<Settings> {
    override val defaultValue: Settings = Settings.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): Settings {
        return try {
            Settings.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            Log.e("SETTINGS", "Cannot read proto. Create default. ${exception.localizedMessage}")
            defaultValue
        }
    }

    override suspend fun writeTo(t: Settings, output: OutputStream) = t.writeTo(output)
}
