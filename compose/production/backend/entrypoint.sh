#!/bin/sh

set -o errexit
set -o pipefail
set -o nounset

# You can set here anything you need before starting the spring boot application
mvn spring-boot:run
