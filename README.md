[![Build Status](https://travis-ci.org/five-in-a-row-challenge/five-in-a-row-challenge-server.svg?branch=master)](https://travis-ci.org/five-in-a-row-challenge/five-in-a-row-challenge-server)

Five in a row challenge
=======================
The **Five in a row challenge** is a multiplayer REST game, for practicing writing a proper REST endpoint with a getting better gomoku A.I.. 
You have to hurry, because the server plays game in every five minute, and you can miss valuable points.
This is the main application for playing **Five in a row challenge**.
This server handles all incoming requests and responses, and save the actual state of the board of every games.

This repository is only for API, you can see the progress of the game in the logs.

## About the game
Teams have to build a "gomoku" REST robot to be able to overcome other teams and win the competition.
The server plays "five-in-a-row" game in every five minute (it can be overwritten), and call the players via rest.
Everyone play against everyone twice, because the game swaps the starting players.

Skeleton repositories in different languages: TODO

Examples:
- https://github.com/five-in-a-row-challenge/five-in-a-row-challenge-random-ai

## Scores 
In every round, the players get scores in the following way:
- in case of winning, the winner gets **5 scores**
- in case of draw, the two players get **2 scores** 
- the loser gets **0 score**

The winner of **Five in a row challenge** is who received the highest number of points in the end of the game.

## How to start the server
1. run MongoDB
2. gradlew bootRun

## How to play game
0. Use swagger docs at http://localhost:8080/swagger-ui.html
1. Post new players (at least 2 players) via swagger
2. Start the game

You can use https://github.com/five-in-a-row-challenge/five-in-a-row-challenge-random-ai to try the game.
Start a **five-in-a-row-challenge-random-ai**, and then post two players with different username, but with the same network address: http://localhost:9999.

![alt text](https://github.com/five-in-a-row-challenge/five-in-a-row-challenge-server/blob/master/FiveInARowSequence.png "Five in a row sequence diagram")

## Technology Stack
- Java 8
- Spring boot 1.4.1
- MongoDB
