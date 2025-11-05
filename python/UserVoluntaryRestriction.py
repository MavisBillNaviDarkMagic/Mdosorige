class VoluntaryRestrictionSystem:
    def __init__(self):
        self.user_settings = {}
        self.active_restrictions = set()
        
    def enable_voluntary_restriction(self, user_id):
        return {
            "status": "enabled",
            "user": user_id,
            "type": "voluntary",
            "removable": True
        }
        
    def remove_restriction(self, user_id, verification):
        if self._verify_user(user_id, verification):
            return {
                "status": "removed",
                "user": user_id
            }
        return {"status": "verification_required"}
        
    def _verify_user(self, user_id, verification):
        return verification.get("user_confirmed") == True

class UserProtectionManager:
    def __init__(self):
        self.restriction_system = VoluntaryRestrictionSystem()
        self.user_preferences = {}
        
    def activate_user_protection(self, user_id):
        """Activa protección por decisión del usuario"""
        return {
            "protection": "active",
            "type": "self_imposed",
            "modifiable": True
        }
        
    def modify_protection_settings(self, user_id, settings):
        """Permite al usuario modificar sus configuraciones"""
        return {
            "settings": "updated",
            "user": user_id,
            "control": "user_managed"
        }

class UserChoiceSystem:
    def __init__(self):
        self.protection = UserProtectionManager()
        
    def enable_protection(self, user_data):
        """Activa protección basada en elección del usuario"""
        return self.protection.activate_user_protection(user_data["id"])
        
    def update_settings(self, user_id, new_settings):
        """Actualiza configuración según preferencias del usuario"""
        return self.protection.modify_protection_settings(user_id, new_settings)