#!/bin/bash
set -eou pipefail

readonly IMAGE=${1:-"flowable/flowable-jre:17.0.8"}
echo "Image name: ${IMAGE}"

echo "Building image..."
docker build -t ${IMAGE} -f Dockerfile .
