package com.miniredis.miniredis.controller;

import com.miniredis.miniredis.domain.IRedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.lang.reflect.InvocationTargetException;

@RestController
public class CommandsController {

    @Autowired
    private IRedisService redisService;

    @GetMapping
    public ResponseEntity<String> executeCommand(@RequestParam String cmd) {
        try {
            Object returnedCommand = this.redisService.executeCommand(cmd);

            return new ResponseEntity<String>(
                    returnedCommand != null ? returnedCommand.toString() : "nil",
                    HttpStatus.OK);
        }
        catch (Exception e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }


}
