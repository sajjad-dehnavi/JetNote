package com.example.domain.repos

import android.content.Context
import com.google.android.exoplayer2.ExoPlayer

interface ExoRepo {
    val getDuration : Long
   suspend fun prepareMediaPlayer(mediaPath: String): ExoPlayer
   suspend fun prepareStreamMediaPlayer(mediaPath: String): ExoPlayer
   suspend fun getMediaDuration(context: Context, path: String): Long

}