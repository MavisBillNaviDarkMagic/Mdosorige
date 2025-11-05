from AudioSystem import UltraHDAudioSystem

class AvatarVoiceController:
    def __init__(self):
        self.audio_system = UltraHDAudioSystem()
        self.active_voices = {}

    def activate_avatar_voice(self, avatar_id, voice_type="diablillo"):
        voice_config = {
            "id": avatar_id,
            "type": voice_type,
            "active": True
        }
        voice_profile = self.audio_system.generate_avatar_voice(voice_type)
        return self.audio_system.process_voice(voice_config)

    def set_voice_effect(self, avatar_id, effect_type):
        if avatar_id in self.active_voices:
            voice_data = {
                "format": "wavUltraHD",
                "effect": effect_type
            }
            return self.audio_system.apply_voice_effects(voice_data)
        return {"status": "error", "message": "Avatar no encontrado"}