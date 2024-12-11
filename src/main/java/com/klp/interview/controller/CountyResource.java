package com.klp.interview.controller;

import com.klp.interview.integration.CountyNotFoundException;
import com.klp.interview.integration.KartverketConsumer;
import com.klp.interview.model.FylkesnavnResponse;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.constraints.Positive;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CountyResource {

    private final KartverketConsumer kartverketConsumer;

    public CountyResource(KartverketConsumer kartverketConsumer) {
        this.kartverketConsumer = kartverketConsumer;
    }

    @Operation(summary = "Get county by number")
    @GetMapping(value = "/county/{countyNumber}", produces = "plain/text")
    public ResponseEntity<String> getCounty(@Validated @Positive @PathVariable String countyNumber) {
        FylkesnavnResponse response = kartverketConsumer.findCountyName(countyNumber);
        return ResponseEntity.ok(response.fylkesnavn());
    }

    @ExceptionHandler(CountyNotFoundException.class)
    public ResponseEntity<String> handleCountyNotFoundException(CountyNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
}
