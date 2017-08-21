package com.qiya.boss.fwapi.controller;

import javax.servlet.http.HttpServletRequest;

import com.qiya.framework.coreapi.BizApiManager;
import com.qiya.framework.def.ConstDef;
import com.qiya.framework.model.ApiOutput;
import com.qiya.framework.model.SearchCondition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/")
public class EntityController {
	@Autowired
	BizApiManager apiManager;

	@RequestMapping(value = "/{entity}/search", produces = { ConstDef.ApiProduces })
	public ApiOutput search(HttpServletRequest request, @RequestBody SearchCondition sc, @PathVariable String entity) {
		return this.apiManager.search(request, sc, entity);
	}

	@RequestMapping(value = "/{entity}/create", produces = { ConstDef.ApiProduces })
	public ApiOutput create(@RequestBody String data, @PathVariable String entity) {
		return this.apiManager.create(data, entity);
	}

	@RequestMapping(value = "/{entity}/read/{id}", produces = { ConstDef.ApiProduces })
	public ApiOutput read(HttpServletRequest request, @PathVariable String entity, @PathVariable String id) {
		return this.apiManager.read(request, entity, id);
	}

	@RequestMapping(value = "/{entity}/update/{id}", produces = { ConstDef.ApiProduces })
	public ApiOutput update(@RequestBody String data, @PathVariable String entity, @PathVariable String id) {
		return this.apiManager.update(data, entity, id);
	}

	@RequestMapping(value = "/{entity}/delete/{id}", produces = { ConstDef.ApiProduces })
	public ApiOutput delete(HttpServletRequest request, @PathVariable String entity, @PathVariable String id) {
		return this.apiManager.delete(request, entity, id);
	}
}
