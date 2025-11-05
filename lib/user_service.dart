import 'dart:convert';
import 'package:flutter/services.dart' show rootBundle;
import 'user_model.dart';

class UserService {
  static final UserService _instance = UserService._internal();

  factory UserService() {
    return _instance;
  }

  UserService._internal();

  static UserService getInstance() {
    return _instance;
  }

  List<User> _users = [];

  List<User> get users => _users;

  Future<void> init() async {
    await loadUsers();
  }

  Future<void> loadUsers() async {
    try {
      final String response = await rootBundle.loadString('assets/users.json');
      final data = await json.decode(response) as List;
      _users = data.map((json) => User.fromJson(json)).toList();
    } catch (e) {
      // Handle error
      print(e);
    }
  }
}
