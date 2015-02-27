#!/bin/sh
cd scripts/gitrepo
git remote update 1>/dev/null 2>&1
git tag -l | grep -e "^[5-9]\{1\}\.[0-9]\{1,3\}\.[0-9]\{1,3\}\.[0-9]\{1,3\}"

cd ..