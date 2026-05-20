package com.example.demo.controller;

import java.time.LocalDate;
import java.util.List;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.Result;
import com.example.demo.repository.DishRepository;
import com.example.demo.repository.ResultRepository;

@Controller
public class DishController {
	private final HttpSession session;
	private final DishRepository dishRepository;
	private final ResultRepository resultRepository;

	public DishController(HttpSession session, DishRepository dishRepository, ResultRepository resultRepository) {
		this.session = session;
		this.dishRepository = dishRepository;
		this.resultRepository = resultRepository;
	}

	//	一覧画面
	@GetMapping("/dishes/result")
	public String index(Model model) {
		List<Result> resultList = resultRepository.findAll();
		model.addAttribute("resultList", resultList);
		return "dishesResult";
	}

	//新規内容登録画面
	@GetMapping("/dishes/add")
	public String create() {
		return "dishesAdd";
	}

	@PostMapping("/dishes/add")
	public String addDishes(

			@RequestParam(defaultValue = "") LocalDate recordDate,
			@RequestParam(defaultValue = "") Integer stapleFood,
			@RequestParam(defaultValue = "") Integer sideDish,
			@RequestParam(defaultValue = "") Integer mainDish,
			@RequestParam(defaultValue = "") Integer milkDish,
			@RequestParam(defaultValue = "") Integer fruitCount,
			@RequestParam(defaultValue = "") String detaiMemo,
			Model model) {

		Integer userId = (Integer) session.getAttribute("userId");

		Result result = new Result();
		result.setUserId(userId);
		result.setRecordDate(LocalDate.now());
		result.setStapleFood(stapleFood);
		result.setSideDish(sideDish);
		result.setMainDish(mainDish);
		result.setMilkDish(milkDish);
		result.setFruitCount(fruitCount);
		result.setDetailMemo(detaiMemo);
		int achievement = sumAchievement(
				stapleFood,
				sideDish,
				mainDish,
				milkDish,
				fruitCount);
		result.setAchievement(achievement);
		resultRepository.save(result);

		return "redirect:/dishes/result";

	}

	//詳細登録画面
	@GetMapping("/dishes/note")
	public String addNote() {
		return "noteAdd";
	}

	//	//更新画面
	//	@GetMapping("/dishes/{id]/edit")
	//	public String edit(@PathVariable Integer id, Model model) {
	//
	//		Result result = resultRepository.findById(id).get();
	//		model.addAttribute("result", result);
	//
	//		return "dishesResult";
	//
	//	}

	private int sumAchievement(Integer stapleFood, Integer sideDish, Integer mainDish, Integer milkDish,
			Integer fruitCount) {
		// TODO 自動生成されたメソッド・スタブ
		return 0;
	}

}
