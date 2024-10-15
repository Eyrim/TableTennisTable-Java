#!/bin/bash

docker build --tag tt .
docker run -i --env-file .env tt
