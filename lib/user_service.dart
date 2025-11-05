import 'dart:convert';
import 'package:flutter/services.dart' show rootBundle;
import 'package:flutter/foundation.dart';
import 'user_model.dart';

class UserService with ChangeNotifier {
  List<User> _users = [];

  List<User> get users => _users;

  Future<void> loadUsers() async {
    final String response = await rootBundle.loadString('assets/users.json');
    final data = await json.decode(response) as List;
    _users = data.map((json) => User.fromJson(json)).toList();
    notifyListeners();
  }
}
