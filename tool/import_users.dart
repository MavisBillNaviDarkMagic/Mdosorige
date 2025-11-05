
import 'dart:convert';
import 'dart:io';

import 'package:cloud_firestore/cloud_firestore.dart';
import 'package:firebase_core/firebase_core.dart';
import 'package:flutter/widgets.dart';

// This script requires the `firebase_options.dart` file to be present in the `lib` directory.
// Please run `flutterfire configure` to generate this file before running the script.
import '../lib/firebase_options.dart';

Future<void> main() async {
  WidgetsFlutterBinding.ensureInitialized();
  await Firebase.initializeApp(
    options: DefaultFirebaseOptions.currentPlatform,
  );

  final file = File('assets/users.json');
  if (!await file.exists()) {
    print('Error: assets/users.json not found.');
    return;
  }

  final json = await file.readAsString();
  final users = jsonDecode(json) as List;

  final firestore = FirebaseFirestore.instance;
  final collection = firestore.collection('users');

  // Use a batch write for efficiency
  final batch = firestore.batch();
  int count = 0;

  for (final userData in users) {
    // Firestore expects a Map<String, dynamic>
    final userMap = Map<String, dynamic>.from(userData as Map);
    final docRef = collection.doc(); // Create a new document with a random ID
    batch.set(docRef, userMap);
    count++;
  }

  try {
    await batch.commit();
    print('Successfully imported $count users in a single batch!');
  } catch (e) {
    print('An error occurred during the batch commit: $e');
  }
}
