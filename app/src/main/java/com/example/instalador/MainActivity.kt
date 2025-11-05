package com.example.instalador

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.Settings
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.example.instalador.databinding.ActivityMainBinding
import java.io.File

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val STORAGE_PERMISSION_CODE = 1000
    private val INSTALL_PERMISSION_CODE = 1001

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupUI()

        // Manejar el intent si se abre un APK
        intent?.data?.let { uri ->
            if (uri.path?.endsWith(".apk") == true) {
                showLoading(true)
                if (prepareApkFile(uri)) {
                    checkPermissionsAndInstall()
                } else {
                    showLoading(false)
                }
            }
        }
    }

    private fun setupUI() {
        binding.installButton.setOnClickListener {
            checkPermissionsAndInstall()
        }
    }

    private fun checkPermissionsAndInstall() {
        showLoading(true)
        when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.R -> {
                if (!Environment.isExternalStorageManager()) {
                    requestStoragePermission()
                } else {
                    checkInstallPermission()
                }
            }
            else -> {
                if (checkStoragePermission()) {
                    checkInstallPermission()
                }
            }
        }
    }

    private fun requestStoragePermission() {
        try {
            val intent = Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION)
            startActivityForResult(intent, STORAGE_PERMISSION_CODE)
        } catch (e: Exception) {
            updateStatus("Error: No se puede solicitar permiso de almacenamiento")
            showLoading(false)
        }
    }

    private fun checkStoragePermission(): Boolean {
        return if (hasStoragePermission()) {
            true
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ),
                STORAGE_PERMISSION_CODE
            )
            false
        }
    }

    private fun hasStoragePermission() = ContextCompat.checkSelfPermission(
        this,
        Manifest.permission.READ_EXTERNAL_STORAGE
    ) == PackageManager.PERMISSION_GRANTED

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
            if (!apkFile.exists()) {
                updateStatus("Error: APK no encontrado")
                showLoading(false)
                return
            }

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
            updateStatus("Iniciando instalación...")
        } catch (e: Exception) {
            updateStatus("Error: ${e.message}")
        } finally {
            showLoading(false)
        }
    }

    private fun prepareApkFile(sourceUri: Uri): Boolean {
        try {
            val destFile = File(getExternalFilesDir(null), "app.apk")
            contentResolver.openInputStream(sourceUri)?.use { input ->
                destFile.outputStream().use { output ->
                    input.copyTo(output)
                }
            }
            updateStatus("APK preparado para instalación")
            return true
        } catch (e: Exception) {
            updateStatus("Error preparando APK: ${e.message}")
            return false
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            STORAGE_PERMISSION_CODE -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    if (Environment.isExternalStorageManager()) {
                        checkInstallPermission()
                    } else {
                        updateStatus("Permiso de almacenamiento denegado")
                        showLoading(false)
                    }
                }
            }
            INSTALL_PERMISSION_CODE -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    if (packageManager.canRequestPackageInstalls()) {
                        instalarApk()
                    } else {
                        updateStatus("Permiso de instalación denegado")
                        showLoading(false)
                    }
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
                    updateStatus("Permiso de almacenamiento denegado")
                    showLoading(false)
                }
            }
        }
    }

    private fun showLoading(show: Boolean) {
        binding.progressBar.visibility = if (show) View.VISIBLE else View.GONE
        binding.installButton.isEnabled = !show
    }

    private fun updateStatus(message: String) {
        binding.statusText.text = message
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
