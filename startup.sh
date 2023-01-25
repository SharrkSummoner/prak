#!/bin/bash

bash mvnw compile
bash mvnw exec:java -Dexec.mainClass="package com.example.prak.Main"