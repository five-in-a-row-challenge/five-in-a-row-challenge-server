[![Build Status](https://travis-ci.org/zeldan/five-in-a-row-challenge.svg?branch=master)](https://travis-ci.org/zeldan/five-in-a-row-challenge)

Five in a row challenge
=======================
The **Five in a row challenge** is a multiplayer REST games, for practicing writing a proper REST endpoint with a getting better gomoku A.I.. 
You have to hurry, because the server plays games in every minute, and you can miss valuable points.
This is the main application for playing **Five in a row challenge**.
This server handles all incoming requests and responses, and save the actual state of the board of every games.

## About the game
Teams have to build a "gomoku" robot to be able to overcome other teams and win the competition.
The server plays "five-in-a-row" games in every minutes, and call the players via rest.

## How to start the server
- gradlew bootRun

## How to play game
TODO: create a long description

![alt text](https://github.com/zeldan/five-in-a-row-challenge/blob/master/FiveInARowSequence.png "Five in a row sequence diagram")

## Technology Stack
- Java 8
- Spring boot 1.4.1
