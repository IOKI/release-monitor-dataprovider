#!/bin/sh
cd scripts/gitrepo
git remote update 1>/dev/null 2>&1
git branch -avv  | awk '{print $1}' |  grep "releases" | grep -e "[5-9]\{1\}\.[0-9]\{1,3\}\.[0-9]\{1,3\}" | sed "s#remotes/##g"
cd ..