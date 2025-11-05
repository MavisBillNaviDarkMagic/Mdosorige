import os
import shutil
from pathlib import Path

class APKHandler:
    def __init__(self):
        self.apk_dir = Path("apks")
        self.apk_dir.mkdir(exist_ok=True)
    
    def validate_apk(self, apk_path):
        """Valida si el archivo es un APK válido"""
        if not apk_path.endswith('.apk'):
            raise ValueError("El archivo debe ser un APK")
        if not os.path.exists(apk_path):
            raise FileNotFoundError("APK no encontrado")
        return True

    def copy_apk(self, source_path, destination_name):
        """Copia el APK al directorio de trabajo"""
        try:
            self.validate_apk(source_path)
            dest_path = self.apk_dir / destination_name
            shutil.copy2(source_path, dest_path)
            return str(dest_path)
        except Exception as e:
            print(f"Error al copiar APK: {e}")
            return None

if __name__ == "__main__":
    handler = APKHandler()
    # Ejemplo de uso
    # handler.copy_apk("ruta/al/archivo.apk", "app.apk")
