package com.whalar.gameofthrones.controller;

import com.whalar.gameofthrones.service.DatabaseFillerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("db-filler")
public class DatabaseFillerController {

	private final DatabaseFillerService databaseFillerService;

	@GetMapping("")
	public void fillDb() {
		databaseFillerService.execute();
	}
}
