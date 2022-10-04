package com.ace.soundrecorder

import android.annotation.SuppressLint
import android.media.MediaRecorder
import android.os.Bundle
import android.os.Environment
import androidx.appcompat.app.AppCompatActivity
import com.ace.soundrecorder.databinding.ActivityRecordBinding
import java.io.File
import java.io.IOException

class RecordActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRecordBinding
    private var output: String? = null
    private var recorder: MediaRecorder? = null
    private val dir: File = File(Environment.getExternalStorageDirectory().absolutePath + "/soundrecorder/")
    private var isRecording = false


    init {
        try{
            val recorderDirectory = File(Environment.getExternalStorageDirectory().absolutePath+"/soundrecorder/")
            recorderDirectory.mkdirs()
        }catch (e: IOException){
            e.printStackTrace()
        }

        if(dir.exists()){
            val count = dir.listFiles().size
            output = Environment.getExternalStorageDirectory().absolutePath + "/soundrecorder/recording"+count+".mp3"
        }

        recorder = MediaRecorder()

        recorder?.setAudioSource(MediaRecorder.AudioSource.MIC)
        recorder?.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
        recorder?.setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
        recorder?.setOutputFile(output)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnRecord.setOnClickListener {
            when {
                isRecording -> stopRecording()
                else -> startRecording()
            }
        }
    }


    @SuppressLint("RestrictedApi")
    fun startRecording() {
        isRecording = true

        try {
            println("Starting recording!")
            recorder?.prepare()
            recorder?.start()
        } catch (e: IllegalStateException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        binding.btnRecord.setImageResource(R.drawable.ic_stop)
    }


    @SuppressLint("RestrictedApi")
    fun stopRecording(){
        recorder?.stop()
        recorder?.release()

        initRecorder()

        binding.btnRecord.setImageResource(R.drawable.ic_record)
        isRecording = false

    }

    private fun initRecorder() {
        recorder = MediaRecorder()

        if(dir.exists()){
            val count = dir.listFiles().size
            output = Environment.getExternalStorageDirectory().absolutePath + "/soundrecorder/recording"+count+".mp3"
        }

        recorder?.setAudioSource(MediaRecorder.AudioSource.MIC)
        recorder?.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
        recorder?.setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
        recorder?.setOutputFile(output)
    }
}
