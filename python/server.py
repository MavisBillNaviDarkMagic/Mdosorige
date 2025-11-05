from flask import Flask, jsonify
import subprocess

app = Flask(__name__)

@app.route('/install', methods=['POST'])
def install():
    try:
        result = subprocess.run(['python', 'AppStoreIntegration.py'], capture_output=True, text=True, check=True)
        return jsonify({'output': result.stdout})
    except subprocess.CalledProcessError as e:
        return jsonify({'error': e.stderr}), 500

@app.route('/integrate', methods=['POST'])
def integrate():
    try:
        result = subprocess.run(['python', 'UniversalIntegration.py'], capture_output=True, text=True, check=True)
        return jsonify({'output': result.stdout})
    except subprocess.CalledProcessError as e:
        return jsonify({'error': e.stderr}), 500
