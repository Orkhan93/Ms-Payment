package com.example.mspayment.client;

import com.example.mspayment.dto.CountryDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "ms-country" , url = "${client.ms-country.endpoint}")
public interface CountryClient {

    @GetMapping("/countries")
    List<CountryDto> getAllAvailableCountries(@RequestParam String currency);

}