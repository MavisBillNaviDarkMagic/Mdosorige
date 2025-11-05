class UnifiedIPSystem:
    def __init__(self):
        self.license_system = UnifiedLicenseSystem()
        self.collaboration_system = CollaborationSystem()
        self.security_level = 7
        self.quantum_protection = True
        
    def register_ip(self, ip_data, partner_id=None):
        """Registra propiedad intelectual con colaboración opcional"""
        registration = self.license_system.register_intellectual_property(ip_data)
        
        if partner_id:
            collaboration = self.collaboration_system.start_collaboration(
                partner_id, ip_data
            )
            registration["collaboration"] = collaboration
            
        return registration
        
    def verify_ip_status(self, ip_id):
        """Verifica estado de propiedad intelectual"""
        license_status = self.license_system.verify_ownership(ip_id)
        return {
            "status": license_status["status"],
            "owner": license_status["owner"],
            "protection": "quantum_maximum",
            "security": "level_7"
        }

# Inicialización del sistema
def initialize_ip_system():
    system = UnifiedIPSystem()
    return {
        "status": "initialized",
        "security": "quantum_maximum",
        "access_level": 7,
        "owner": "MaVis_Navi"
    }