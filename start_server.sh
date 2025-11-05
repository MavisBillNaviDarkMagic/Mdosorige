#!/bin/bash

# Find and kill any process using port 5001 to ensure a clean start.
PID=$(lsof -t -i:5001)
if [ -n "$PID" ]; then
  kill -9 $PID
fi

# Start the new server as a daemon.
# Gunicorn will be run from the 'python' directory.
# The PID file will be created at 'python/gunicorn.pid'.
nix-shell --run "gunicorn --bind 127.0.0.1:5001 --daemon --pid gunicorn.pid server:app --chdir python"
