import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import 'user_service.dart';

class UserListScreen extends StatelessWidget {
  const UserListScreen({super.key});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('User Directory'),
      ),
      body: Consumer<UserService>(
        builder: (context, userService, child) {
          if (userService.users.isEmpty) {
            userService.loadUsers();
            return const Center(child: CircularProgressIndicator());
          }

          return ListView.builder(
            itemCount: userService.users.length,
            itemBuilder: (context, index) {
              final user = userService.users[index];
              return ListTile(
                title: Text(user.name),
                subtitle: Text(user.email),
              );
            },
          );
        },
      ),
    );
  }
}
