from androguard.core.bytecodes.apk import APK

def analyze_apk(apk_path):
    """Analiza un archivo APK y retorna información básica"""
    try:
        apk = APK(apk_path)
        return {
            "package": apk.get_package(),
            "version_name": apk.get_androidversion_name(),
            "version_code": apk.get_androidversion_code(),
            "permissions": apk.get_permissions()
        }
    except Exception as e:
        return {"error": str(e)}

def verify_signature(apk_path):
    """Verifica la firma del APK"""
    try:
        apk = APK(apk_path)
        return apk.is_signed()
    except Exception:
        return False
