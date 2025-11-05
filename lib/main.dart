
import 'package:flutter/material.dart';
import 'package:my_app/menu_screen.dart';
import 'package:my_app/setup_portal.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'MDOS Setup',
      theme: ThemeData(
        primarySwatch: Colors.blue,
      ),
      home: const SetupPortalScreen(),
      routes: {
        '/menu': (context) => const MenuScreen(),
      },
    );
  }
}
