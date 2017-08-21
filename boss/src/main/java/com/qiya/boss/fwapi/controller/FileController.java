package com.qiya.boss.fwapi.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.qiya.framework.coreservice.file.FileUploadResult;
import com.qiya.framework.coreservice.file.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("/")
public class FileController {
	@Autowired
	UploadService uploadService;

	@RequestMapping(value = "/upload4demo", method = RequestMethod.POST)
	@ResponseBody
	String upload4demo(@RequestParam("upfile") MultipartFile file, HttpServletResponse resp) {
		resp.setContentType(MediaType.TEXT_HTML_VALUE);
		return "{'original': 'demo.jpg','name': 'demo.jpg','url': 'http://192.168.1.141:9010/file/4abc55f3b0804aa6b86a7d2615db1eb8','size': '99697','type': '.jpg','state': 'SUCCESS'}";
	}

	@RequestMapping(value = "/uploadfile", method = RequestMethod.POST)
	@ResponseBody
	FileUploadResult uploadByUEditer(HttpServletRequest request) {
		return this.uploadService.uploadByUEditer(request);
	}

	@RequestMapping(value = "/file/{name}")
	public void findImage(@PathVariable("name") String name, HttpServletResponse resp) {
		this.uploadService.readFile(name, resp);
	}
}
