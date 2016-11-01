[![Build Status](https://travis-ci.org/zeldan/five-in-a-row-challenge.svg?branch=master)](https://travis-ci.org/zeldan/five-in-a-row-challenge)

Five in a row challenge
=======================
The **Five in a row challenge** is a multiplayer REST game, for practicing writing a proper REST endpoint with a getting better gomoku A.I.. 
You have to hurry, because the server plays game in every minute, and you can miss valuable points.
This is the main application for playing **Five in a row challenge**.
This server handles all incoming requests and responses, and save the actual state of the board of every games.

## About the game
Teams have to build a "gomoku" robot to be able to overcome other teams and win the competition.
The server plays "five-in-a-row" games in every minutes, and call the players via rest.

## How to start the server
- gradlew bootRun

## How to play game
0. Use swagger docs at http://localhost:8080/swagger-ui.html
1. Post new players (at least 2 players) via swagger
2. Start the game

You can use https://github.com/zeldan/five-in-a-row-challenge-random-ai to try the game.
Start a **five-in-a-row-challenge-random-ai**, and then post two players with different username, but with the same network address: http://localhost:9999.

![alt text](https://github.com/zeldan/five-in-a-row-challenge/blob/master/FiveInARowSequence.png "Five in a row sequence diagram")

## UI ##
in work

## Technology Stack
- Java 8
- Spring boot 1.4.1
