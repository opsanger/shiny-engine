package com.klp.interview.controller;

import com.klp.interview.integration.KartverketConsumer;
import com.klp.interview.model.FylkesnavnResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CountryResource {

    private final KartverketConsumer kartverketConsumer;

    public CountryResource(KartverketConsumer kartverketConsumer) {
        this.kartverketConsumer = kartverketConsumer;
    }

    @GetMapping(value = "/county/{countyNumber}", produces = "plain/text")
    public ResponseEntity<String> getCounty(@PathVariable String countyNumber) {
        FylkesnavnResponse response = kartverketConsumer.findCountyName(countyNumber);
        return ResponseEntity.ok(response.fylkesnavn());
    }
}
