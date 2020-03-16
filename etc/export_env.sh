#!/bin/bash

cat ./.env | while read line; do
    if [ -n "$line" ]; then
        echo $line
        export $line
    fi
done