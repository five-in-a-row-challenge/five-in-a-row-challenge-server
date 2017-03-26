package com.gomoku.controller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Player with given id does not exist")
public class PlayerNotFoundException extends RuntimeException {

}
