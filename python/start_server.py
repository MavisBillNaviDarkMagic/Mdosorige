import socket
import subprocess
import os

def find_free_port():
    with socket.socket(socket.AF_INET, socket.SOCK_STREAM) as s:
        s.bind(('', 0))
        return s.getsockname()[1]

def start_server():
    port = find_free_port()
    print(f"Found free port: {port}")
    os.environ['PORT'] = str(port)
    subprocess.run(['gunicorn', '--bind', f'0.0.0.0:{port}', 'python.server:app'])

if __name__ == '__main__':
    start_server()
