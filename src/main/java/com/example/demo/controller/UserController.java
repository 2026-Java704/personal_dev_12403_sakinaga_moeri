package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;

@Controller
public class UserController {

	private final UserRepository userRepository;

	public UserController(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	//	ログイン画面の表示
	@GetMapping({ "/", "/login", "logout" })
	public String index() {
		return "login";
	}

	//ログインを実行
	@PostMapping("/login")

	public String login(
			@RequestParam String email,
			@RequestParam String password,
			Model model) {

		List<User> userList = userRepository.findByEmailAndPassword(email, password);

		if ((email == null || email.length() == 0) && (password == null || password.length() == 0)) {
			model.addAttribute("message", "メールアドレスを入力してください");
			model.addAttribute("message1", "パスワードを入力してください");
			return "login";

		} else if (email == null || email.length() == 0) {
			model.addAttribute("message", "メールアドレスを入力してください");
			return "login";

		} else if (password == null || password.length() == 0) {
			model.addAttribute("message", "パスワードを入力してください");
			return "login";

		} else if (userList == null || userList.size() == 0) {
			model.addAttribute("message;", "メールアドレスとパスワードが一致しませんでした");
			return "login";

		}
		model.addAttribute("email", email);
		model.addAttribute("password", password);

		return "redirect:/dishes/result";

	}

	@GetMapping("/users/add")
	public String create() {
		return "user";
	}

	@PostMapping("/users/add")
	public String add(

			@RequestParam(defaultValue = "") String name,
			@RequestParam(defaultValue = "") String email,
			@RequestParam(defaultValue = "") String password,
			@RequestParam(defaultValue = "") Integer age,
			@RequestParam(defaultValue = "") Integer gender,
			Model model) {
		List<String> errorList = new ArrayList<>();

		if (name == null || name.length() == 0) {
			errorList.add("名前は必須です");
		}
		if (email == null || email.length() == 0) {
			errorList.add("メールアドレスは必須です");
		}
		if (password == null || password.length() == 0) {
			errorList.add("パスワードは必須です");
		}
		if (age == null) {
			errorList.add("年齢は必須です");
		}
		if (gender == 0) {
			errorList.add("性別を選択してください");
		}
		List<User> userList = userRepository.findByEmail(email);
		if (userList != null && userList.size() > 0) {
			errorList.add("登録済みのメールアドレスです");
		}

		if (errorList.size() > 0) {
			model.addAttribute("errorList", errorList);
			model.addAttribute("name", name);
			model.addAttribute("email", email);
			model.addAttribute("password", password);
			model.addAttribute("age", age);
			model.addAttribute("gender", gender);

			return "user";

		}

		User user = new User(name, email, password, age, gender);
		userRepository.save(user);

		return "redirect:/login";
	}

}
