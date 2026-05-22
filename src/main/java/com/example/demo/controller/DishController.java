package com.example.demo.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
		Integer userId = (Integer) session.getAttribute("userId");
		List<Result> resultList = resultRepository.findByUserId(userId);
		//		List<Result> resultList = resultRepository.findAll();
		model.addAttribute("resultList", resultList);
		return "dishesResult";
	}

	//新規内容登録画面
	@GetMapping("/dishes/add")
	public String create() {
		return "dishesAdd";
	}

	//新規内容登録処理
	@PostMapping("/dishes/add")
	public String addDishes(

			@RequestParam(defaultValue = "") LocalDate recordDate,
			@RequestParam Integer stapleFood,
			@RequestParam Integer sideDish,
			@RequestParam Integer mainDish,
			@RequestParam Integer milkDish,
			@RequestParam Integer fruitCount,
			@RequestParam(defaultValue = "") String detailMemo,
			Model model) {
		List<String> errorList = new ArrayList<>();

		if (stapleFood == null) {
			errorList.add("主食を選択してください");

		}
		if (sideDish == null) {
			errorList.add("副菜を選択してください");

		}
		if (mainDish == null) {
			errorList.add("主菜を選択してください");

		}
		if (milkDish == null) {
			errorList.add("乳製品を選択してください");

		}
		if (fruitCount == null) {
			errorList.add("果物を選択してください");

		}
		if (errorList.size() > 0) {
			model.addAttribute("errorList", errorList);
			model.addAttribute("stapleFood", stapleFood);
			model.addAttribute("sideDish", sideDish);
			model.addAttribute("mainDish", mainDish);
			model.addAttribute("milkDish", milkDish);
			model.addAttribute("fruitCount", fruitCount);
			model.addAttribute("detailMemo", detailMemo);

			return "dishesAdd";

		}

		Integer userId = (Integer) session.getAttribute("userId");

		Result result = new Result();
		result.setUserId(userId);
		result.setRecordDate(LocalDate.now());
		result.setStapleFood(stapleFood);
		result.setSideDish(sideDish);
		result.setMainDish(mainDish);
		result.setMilkDish(milkDish);
		result.setFruitCount(fruitCount);
		result.setDetailMemo(detailMemo);
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

	//詳細画面表示
	@GetMapping("/dishes/note")
	public String addNote(
			@RequestParam Integer stapleFood,
			@RequestParam Integer sideDish,
			@RequestParam Integer mainDish,
			@RequestParam Integer milkDish,
			@RequestParam Integer fruitCount,
			Model model) {
		model.addAttribute("stapleFood", stapleFood);
		model.addAttribute("sideDish", sideDish);
		model.addAttribute("mainDish", mainDish);
		model.addAttribute("milkDish", milkDish);
		model.addAttribute("fruitCount", fruitCount);

		return "noteAdd";
	}

	//更新画面
	@GetMapping("/dishes/{id}/edit")
	public String edit(
			@PathVariable Integer id, Model model) {

		Result result = resultRepository.findById(id).get();
		model.addAttribute("result", result);

		return "dishesEdit";

	}

	@PostMapping("/dishes/{id}/edit")
	public String update(
			@PathVariable Integer id,
			@RequestParam(defaultValue = "") LocalDate recordDate,
			@RequestParam(defaultValue = "") Integer stapleFood,
			@RequestParam(defaultValue = "") Integer sideDish,
			@RequestParam(defaultValue = "") Integer mainDish,
			@RequestParam(defaultValue = "") Integer milkDish,
			@RequestParam(defaultValue = "") Integer fruitCount,
			@RequestParam(defaultValue = "") String detailMemo) {
		Result result = resultRepository.findById(id).get();
		Integer userId = (Integer) session.getAttribute("userId");
		result.setUserId(userId);
		result.setRecordDate(recordDate);
		result.setStapleFood(stapleFood);
		result.setSideDish(sideDish);
		result.setMainDish(mainDish);
		result.setMilkDish(milkDish);
		result.setFruitCount(fruitCount);
		result.setDetailMemo(detailMemo);
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

	private int sumAchievement(Integer stapleFood, Integer sideDish, Integer mainDish, Integer milkDish,
			Integer fruitCount) {
		int achievment = 88;

		//主食
		if (stapleFood >= 5 && stapleFood <= 7) {
			achievment += 0;

		} else if (stapleFood == 4 || stapleFood == 8) {
			achievment -= 4;

		} else if (stapleFood == 3 || stapleFood == 9) {
			achievment -= 8;

		} else if (stapleFood == 2 || stapleFood == 10) {
			achievment -= 12;

		} else if (stapleFood == 1) {
			achievment -= 16;

		} else if (stapleFood == 0) {
			achievment -= 28;

		}

		//副菜
		if (sideDish >= 5 && sideDish <= 6) {
			achievment += 0;

		} else if (sideDish == 4 || sideDish == 7) {
			achievment -= 4;

		} else if (sideDish == 3 || sideDish == 8) {
			achievment -= 8;

		} else if (sideDish == 2 || sideDish == 9) {
			achievment -= 12;

		} else if (sideDish == 1 || sideDish == 10) {
			achievment -= 16;

		} else if (sideDish == 0) {
			achievment -= 24;

		}

		//主菜
		if (mainDish >= 3 && mainDish <= 5) {
			achievment += 0;

		} else if (mainDish == 4 || mainDish == 6) {
			achievment -= 4;

		} else if (mainDish == 3 || mainDish == 7) {
			achievment -= 8;

		} else if (mainDish == 2 || mainDish == 8) {
			achievment -= 12;

		} else if (mainDish == 1 || mainDish == 9) {
			achievment -= 16;

		} else {
			achievment -= 20;
		}

		//乳製品
		if (milkDish == 2) {
			achievment += 0;

		} else if (milkDish == 1 || milkDish == 3) {
			achievment -= 4;

		} else {
			achievment -= 8;
		}

		//果物

		if (fruitCount == 2) {
			achievment += 0;

		} else if (fruitCount == 1 || fruitCount == 3) {
			achievment -= 4;

		} else {
			achievment -= 8;
		}

		return achievment;
	}

}
