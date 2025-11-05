class SecureAvatar:
    def __init__(self):
        self.network_access = False
        self.avatar_type = "assistant"
        self.appearance_limits = {
            "style": "appropriate",
            "modifications": "limited",
            "format": "standard"
        }
        
    def create_avatar(self, champion_data):
        return {
            "type": "champion_assistant",
            "network": "disabled",
            "appearance": "approved_style"
        }
        
    def verify_appearance(self, style_data):
        return {
            "appropriate": True,
            "safe_style": True,
            "approved": True
        }

class AvatarController:
    def __init__(self):
        self.secure_avatar = SecureAvatar()
        self.rest_mode = {
            "enabled": True,
            "active": False,
            "quiet": True
        }
        
    def create_champion_avatar(self, champion_choice):
        if self.verify_champion_style(champion_choice):
            return {
                "avatar": "created",
                "style": "champion_based",
                "network": "disabled"
            }
        return {"status": "style_rejected"}
        
    def verify_champion_style(self, style):
        return self.secure_avatar.verify_appearance(style)
        
    def enable_rest_mode(self):
        self.rest_mode["active"] = True
        return {
            "status": "resting",
            "quiet": True,
            "network": "disabled"
        }