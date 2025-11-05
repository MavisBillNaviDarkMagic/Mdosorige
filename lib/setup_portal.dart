
import 'package:flutter/material.dart';
import 'package:my_app/user_service.dart';

class SetupPortal {
  static Future<void> init() async {
    // Simulate a delay for initialization
    await Future.delayed(const Duration(seconds: 2));
    await UserService.getInstance().init();
  }
}

class SetupPortalScreen extends StatefulWidget {
  const SetupPortalScreen({super.key});

  @override
  _SetupPortalScreenState createState() => _SetupPortalScreenState();
}

class _SetupPortalScreenState extends State<SetupPortalScreen> {
  String _status = 'Initializing...';

  @override
  void initState() {
    super.initState();
    _initialize();
  }

  Future<void> _initialize() async {
    try {
      setState(() {
        _status = 'Initializing services...';
      });
      await SetupPortal.init();
      setState(() {
        _status = 'Services initialized successfully!';
      });
      // Navigate to the main screen after initialization
      Navigator.of(context).pushReplacementNamed('/menu');
    } catch (e) {
      setState(() {
        _status = 'Error initializing services: $e';
      });
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            const CircularProgressIndicator(),
            const SizedBox(height: 20),
            Text(_status),
          ],
        ),
      ),
    );
  }
}
