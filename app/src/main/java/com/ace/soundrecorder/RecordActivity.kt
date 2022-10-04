package com.ace.soundrecorder

import android.media.MediaRecorder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.widget.Toast
import com.ace.soundrecorder.databinding.ActivityRecordBinding
import java.io.IOException

class RecordActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRecordBinding
    private lateinit var recorder: MediaRecorder
    private var output: String? = null
    private var isRecording = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        recorder = MediaRecorder()
        output = Environment.getExternalStorageDirectory().absolutePath + "/recording.mp3"

        recorder.setAudioSource(MediaRecorder.AudioSource.MIC)
        recorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
        recorder.setOutputFile(output)

        binding.btnRecord.setOnClickListener {
            when {
                isRecording -> stopRecording()
                else -> startRecording()
            }
        }
    }


    private fun startRecording() {
        isRecording = true
        try {
            recorder?.prepare()
            recorder?.start()
            Toast.makeText(this, "Recording started!", Toast.LENGTH_SHORT).show()
        } catch (e: IllegalStateException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        binding.btnRecord.setImageResource(R.drawable.ic_stop)
    }


    private fun stopRecording() {
//        recorder.apply {
//            stop()
//            release()
//        }
        binding.btnRecord.setImageResource(R.drawable.ic_record)
        isRecording = false
    }
}
