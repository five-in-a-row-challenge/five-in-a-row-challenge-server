package com.gomoku.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "There is less than 2 players in this game")
public class MinimumPlayerViolationException extends RuntimeException {
}
