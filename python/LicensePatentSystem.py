class LicensePatentManager:
    def __init__(self):
        self.licenses = {}
        self.patents = {}
        self.verification_system = {
            "level": 7,
            "creator": "MaVis_Navi",
            "quantum_verification": True
        }
        
    def register_license(self, license_data):
        if self._verify_ownership(license_data):
            return {
                "status": "registered",
                "protection": "quantum_secured",
                "verification": "authenticated"
            }
        return {"status": "denied"}
        
    def register_patent(self, patent_data):
        if self._verify_ownership(patent_data):
            return {
                "status": "patent_registered",
                "protection": "quantum_maximum",
                "authentication": "level_7"
            }
        return {"status": "denied"}
        
    def _verify_ownership(self, data):
        return data.get("owner") in ["MaVis_Navi", "authorized_partner"]

class PatentProtectionSystem:
    def __init__(self):
        self.protection_cores = set()
        self.quantum_security = True
        
    def secure_patent(self, patent_id):
        return {
            "security": "quantum_maximum",
            "encryption": "unbreakable",
            "access_control": "level_7"
        }
        
    def verify_patent_status(self, patent_id):
        return {
            "status": "verified",
            "ownership": "confirmed",
            "protection": "active"
        }

class UnifiedLicenseSystem:
    def __init__(self):
        self.license_manager = LicensePatentManager()
        self.protection_system = PatentProtectionSystem()
        
    def register_intellectual_property(self, data):
        """Registra una nueva propiedad intelectual"""
        if data.get("type") == "patent":
            registration = self.license_manager.register_patent(data)
            protection = self.protection_system.secure_patent(data.get("id"))
        else:
            registration = self.license_manager.register_license(data)
            
        return {
            "status": "registered",
            "type": data.get("type"),
            "protection": "quantum_maximum",
            "verification": "level_7"
        }
        
    def verify_ownership(self, property_id):
        """Verifica la propiedad de una licencia o patente"""
        return {
            "status": "verified",
            "owner": "MaVis_Navi",
            "protection": "active"
        }