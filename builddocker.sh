#!/bin/bash

docker build --tag tt --target "$1" .
docker run -i --env-file .env -p 8000:16476 tt
