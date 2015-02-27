#!/bin/sh
if [ -z $1 ]
then
    echo "Missing first parameter: source branch"
    exit 1
fi

if [ -z $2 ]
then
    echo "Missing second parameter: target branch"
    exit 1
fi

cd scripts/gitrepo
git remote update 1>/dev/null 2>&1
git log $1..$2 --pretty=format:"%h@@@%an@@@%ad@@@%s"
cd ..