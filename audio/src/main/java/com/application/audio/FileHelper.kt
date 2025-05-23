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

        val pcmData = parseDataChunk(byteBuffer)

        val floatList = mutableListOf<Float>()
        val byteBufferWrap = ByteBuffer.wrap(pcmData)
        byteBufferWrap.order(ByteOrder.LITTLE_ENDIAN)

        while (byteBufferWrap.remaining() >= 2) {
            val sample = byteBufferWrap.short
            floatList.add(sample / 32768.0f)
        }

        return floatList.toFloatArray()
    }

    /**
     * Search through byte array for the 'data' chunk in the array. Grab the
     * data content and return it. This will be the pure PCM data filtered of any other
     * metadata.
     */
    private fun parseDataChunk(wavBytes: ByteArray): ByteArray {
        var dataChunk = byteArrayOf()

        // skip over RIFF header of 12 bytes
        var offset = 12

        while (offset + 8 <= wavBytes.size) {
            val chunkId = String(wavBytes, offset, 4)
            val chunkSize = ByteBuffer.wrap(wavBytes, offset + 4, 4)
                .order(ByteOrder.LITTLE_ENDIAN).int

            val chunkDataStart = offset + 8
            val chunkDataEnd = chunkDataStart + chunkSize

            if (chunkDataEnd > wavBytes.size) break

            val content = wavBytes.copyOfRange(chunkDataStart, chunkDataEnd)

            if (chunkId == "data") {
                dataChunk += content
            }

            offset += 8 + chunkSize
        }

        return dataChunk
    }
}