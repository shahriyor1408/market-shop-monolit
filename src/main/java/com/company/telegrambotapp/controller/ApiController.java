package com.company.telegrambotapp.controller;

/**
 * @author "Sohidjonov Shahriyor"
 * @since 07/11/22 Monday 09:19
 * telegram-bot-app/IntelliJ IDEA
 */

public abstract class ApiController<S> {
    protected static final String API = "/api";
    protected static final String VERSION = "/v1";
    protected static final String PATH = API + VERSION;
    protected final S service;

    protected ApiController(S service) {
        this.service = service;
    }
}
