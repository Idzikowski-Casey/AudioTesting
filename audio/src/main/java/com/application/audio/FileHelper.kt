package com.application.audio

import android.content.Context
import java.nio.ByteBuffer
import java.nio.ByteOrder

class FileHelper {
    /**
     * Reads a WAV file as a FloatArray.
     */
    fun readWavAsFloatArray(context: Context, assetFileName: String?): FloatArray? {
        if(assetFileName == null) return null
        val inputStream = context.assets.open(assetFileName)
        val byteBuffer = inputStream.readBytes()
        inputStream.close()

        // Basic WAV parsing: skip first 44 bytes (WAV header)
        val headerSize = 44
        val pcmData = byteBuffer.copyOfRange(headerSize, byteBuffer.size)

        val floatList = mutableListOf<Float>()
        val byteBufferWrap = ByteBuffer.wrap(pcmData)
        byteBufferWrap.order(ByteOrder.LITTLE_ENDIAN)

        while (byteBufferWrap.remaining() >= 2) {
            val sample = byteBufferWrap.short
            floatList.add(sample / 32768.0f)
        }

        return floatList.toFloatArray()
    }
}