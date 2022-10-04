package com.ace.soundrecorder

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.ace.soundrecorder.databinding.ActivityMainBinding


const val REQUEST_CODE = 200
class MainActivity : AppCompatActivity() {

    private var permissions = arrayOf(
        Manifest.permission.RECORD_AUDIO,
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.MANAGE_EXTERNAL_STORAGE)
    private var permissionGranted = false

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        permissionGranted = ActivityCompat.checkSelfPermission(
            this, permissions[0]) == PackageManager.PERMISSION_GRANTED



        binding.btnCekPermission.setOnClickListener {
            if (!permissionGranted) {
                ActivityCompat.requestPermissions(this, permissions, REQUEST_CODE)
            } else {
                Toast.makeText(this, "Semua Permission Diizinkan", Toast.LENGTH_LONG).show()
            }
        }
        binding.btnGoToRecord.setOnClickListener{
            goToRecord()
        }
    }

    private fun goToRecord() {
        val intentGoToRecord = Intent(this, RecordActivity::class.java)
        startActivity(intentGoToRecord)
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_CODE -> {
                permissionGranted = grantResults[0] == PackageManager.PERMISSION_GRANTED
                Toast.makeText(this, "Semua Permission Diizinkan", Toast.LENGTH_LONG).show()
            }
        }
    }
}