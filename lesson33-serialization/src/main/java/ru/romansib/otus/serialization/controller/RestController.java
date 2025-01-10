package ru.romansib.otus.serialization.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.romansib.otus.serialization.service.MainService;

@RequiredArgsConstructor
@org.springframework.web.bind.annotation.RestController
@RequestMapping("/app")
public class RestController {
    private final MainService service;

    @GetMapping()
    public String getResultMap(@RequestHeader("Accept") String acceptHeader) {
        return service.getResultMap(acceptHeader);
    }
}
