
import 'package:flutter_test/flutter_test.dart';
import 'package:my_app/main.dart';

void main() {
  testWidgets('MenuScreen UI Test', (WidgetTester tester) async {
    // Build our app and trigger a frame.
    await tester.pumpWidget(const MyApp());

    // Verify that the title is rendered.
    expect(find.text('MDOS Setup'), findsOneWidget);

    // Verify that the buttons are rendered.
    expect(find.text('Install Program'), findsOneWidget);
    expect(find.text('Integrate with System'), findsOneWidget);
  });
}
