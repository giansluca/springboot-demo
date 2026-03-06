#!/bin/bash

# 1. Pre-authenticate sudo (prevents the background tunnel from hanging on password prompt)
sudo -v

# 2. Start the cluster
minikube start

# 3. Cleanup function (runs when you interrupt the script)
cleanup() {
    echo ""
    echo "Stopping tunnel and cleaning up..."
    pkill -f "minikube tunnel"
    exit
}
# Trap Ctrl+C (SIGINT) and termination signals
trap cleanup SIGINT SIGTERM

# 4. Start the tunnel in the background
echo "Starting minikube tunnel in the background..."
minikube tunnel > /dev/null 2>&1 &

echo "--------------------------------------------------"
echo "Kubernetes is ready and the tunnel is active."
echo "Keep this terminal open to maintain the tunnel."
echo "Press Ctrl+C to stop the tunnel and exit."
echo "--------------------------------------------------"

# Keep the script running to wait for the trap
wait

