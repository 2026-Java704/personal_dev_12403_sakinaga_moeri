package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class DishController {

	//	一覧画面
	@GetMapping("/dishes/result")
	public String index() {
		return "dishesResult";
	}

	//新規内容登録画面
	@GetMapping("/dishes/add")
	public String addDishes() {
		return "dishesAdd";
	}

	@PostMapping("/dishes/add")

	//詳細登録画面
	@GetMapping("/dishes/note")
	public String addNote() {
		return "noteAdd";
	}
}
