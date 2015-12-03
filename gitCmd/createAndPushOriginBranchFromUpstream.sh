#!/bin/bash
if [ "help" == "$1" ] || [ "h" == "$1" ] || [ "-h" == "$1" ]; then
    echo "new_branch_name upstream_branch_name"
    exit 1
fi


if [ -z "$1" ]; then
    echo "new branch name is empty"
    exit 1
fi

if [ -z "$2" ]; then
    echo "upstream branch name is empty"
    exit 1
fi

echo "hope you are in the right workspce"

echo "create branch $1 base on upstream $2"

echo "git checkout -t -b $1 upstream/$2"
git checkout -t -b $1 upstream/$2
echo "======================"

echo "git push origin $1"
git push origin $1
echo "======================"

echo "git checkout dev"
git checkout dev
echo "======================"

echo "git branch -d $1"
git branch -d $1
echo "======================"

echo "git checkout $1"
git checkout $1
echo "======================"

echo "git status"
git status
echo "======================"

echo "prefect done!"