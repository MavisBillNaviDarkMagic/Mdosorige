
import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;
import 'dart:convert';

class MenuScreen extends StatefulWidget {
  const MenuScreen({super.key});

  @override
  _MenuScreenState createState() => _MenuScreenState();
}

class _MenuScreenState extends State<MenuScreen> {
  bool _isRunning = false;
  String _output = '';

  Future<void> _runScript(String endpoint) async {
    if (_isRunning) {
      return;
    }
    setState(() {
      _isRunning = true;
      _output = 'Running...';
    });

    try {
      final response = await http.post(Uri.parse('http://127.0.0.1:5000/$endpoint'));
      if (response.statusCode == 200) {
        final decoded = json.decode(response.body);
        setState(() {
          _output = decoded['output'] ?? 'Script finished with no output.';
        });
      } else {
        final decoded = json.decode(response.body);
        setState(() {
          _output = 'Error: ${decoded['error']}';
        });
      }
    } catch (e) {
      setState(() {
        _output = 'Error: $e';
      });
    } finally {
      setState(() {
        _isRunning = false;
      });
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('MDOS Setup'),
      ),
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: <Widget>[
            ElevatedButton(
              onPressed: () => _runScript('install'),
              child: const Text('Install Program'),
            ),
            const SizedBox(height: 20),
            ElevatedButton(
              onPressed: () => _runScript('integrate'),
              child: const Text('Integrate with System'),
            ),
            const SizedBox(height: 20),
            if (_isRunning)
              const CircularProgressIndicator()
            else
              Text(_output),
          ],
        ),
      ),
    );
  }
}
