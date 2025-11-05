class UserRestrictionInterface:
    def __init__(self):
        self.choice_system = UserChoiceSystem()
        self.user_interface = {
            "enabled": True,
            "language": "auto",
            "accessibility": "maximum"
        }
        
    def show_restriction_options(self):
        return {
            "options": [
                "Activar restricción de contenido",
                "Personalizar restricciones",
                "Establecer nivel de protección",
                "Confirmar activación"
            ],
            "type": "user_choice",
            "reversible": True
        }
        
    def process_user_choice(self, user_id, choice):
        return {
            "status": "processed",
            "action": "restriction_enabled",
            "user_controlled": True
        }

class RestrictionWizard:
    def __init__(self):
        self.interface = UserRestrictionInterface()
        
    def start_restriction_process(self, user_id):
        """Inicia el proceso de restricción voluntaria"""
        options = self.interface.show_restriction_options()
        return {
            "wizard_started": True,
            "user": user_id,
            "steps": "interactive",
            "type": "self_managed"
        }
        
    def complete_setup(self, user_id, choices):
        """Completa la configuración de restricciones"""
        return self.interface.process_user_choice(user_id, choices)