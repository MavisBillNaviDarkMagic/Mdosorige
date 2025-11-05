package com.example.app

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.Settings
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.io.File

class MainActivity : AppCompatActivity() {
    private val STORAGE_PERMISSION_CODE = 1000
    private val INSTALL_PERMISSION_CODE = 1001

    private lateinit var statusText: TextView
    private lateinit var installButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        statusText = findViewById(R.id.statusText)
        installButton = findViewById(R.id.installButton)

        installButton.setOnClickListener {
            checkPermissionsAndInstall()
        }
    }

    private fun checkPermissionsAndInstall() {
        when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.R -> {
                if (!Environment.isExternalStorageManager()) {
                    try {
                        val intent = Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION)
                        startActivityForResult(intent, STORAGE_PERMISSION_CODE)
                    } catch (e: Exception) {
                        statusText.text = "Error: No se puede solicitar permiso de almacenamiento"
                    }
                } else {
                    checkInstallPermission()
                }
            }
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.M -> {
                if (checkStoragePermission()) {
                    checkInstallPermission()
                }
            }
            else -> {
                checkInstallPermission()
            }
        }
    }

    private fun checkStoragePermission(): Boolean {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ),
                STORAGE_PERMISSION_CODE
            )
            return false
        }
        return true
    }

    private fun checkInstallPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (!packageManager.canRequestPackageInstalls()) {
                startActivityForResult(
                    Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES)
                        .setData(Uri.parse("package:$packageName")),
                    INSTALL_PERMISSION_CODE
                )
            } else {
                instalarApk()
            }
        } else {
            instalarApk()
        }
    }

    private fun instalarApk() {
        try {
            val apkFile = File(getExternalFilesDir(null), "app.apk")
            val apkUri = FileProvider.getUriForFile(
                this,
                "${packageName}.fileprovider",
                apkFile
            )

            val intent = Intent(Intent.ACTION_VIEW).apply {
                setDataAndType(apkUri, "application/vnd.android.package-archive")
                flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
            }
            startActivity(intent)

        } catch (e: Exception) {
            statusText.text = "Error: ${e.message}"
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == INSTALL_PERMISSION_CODE) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                if (packageManager.canRequestPackageInstalls()) {
                    instalarApk()
                } else {
                    statusText.text = "Permiso de instalación denegado"
                }
            }
        } else if (requestCode == STORAGE_PERMISSION_CODE) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                if (Environment.isExternalStorageManager()) {
                    checkInstallPermission()
                } else {
                    statusText.text = "Permiso de almacenamiento denegado"
                }
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            STORAGE_PERMISSION_CODE -> {
                if (grantResults.isNotEmpty() &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    checkInstallPermission()
                } else {
                    statusText.text = "Permiso de almacenamiento denegado"
                }
            }
        }
    }

    private fun mostrarConfiguracion() {
        Toast.makeText(this, "Configuración", Toast.LENGTH_SHORT).show()
    }

    private fun mostrarAcercaDe() {
        Toast.makeText(this, "Acerca de la aplicación", Toast.LENGTH_SHORT).show()
    }
}
