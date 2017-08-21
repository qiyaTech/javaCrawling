package com.qiya.boss.fwapi.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ops")
public class BaseOpsController {
	@RequestMapping(value = "/heartbeat")
	public String heartbeat() {
		return "live";
	}
}
