package com.example.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Random;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.Employee;
import com.example.form.InsertEmployeeForm;
import com.example.form.UpdateEmployeeForm;
import com.example.service.EmployeeService;

import jakarta.servlet.http.HttpSession;

/**
 * 従業員情報を操作するコントローラー.
 * 
 * @author igamasayuki
 *
 */
@Controller
@RequestMapping("/employee")
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;

	/**
	 * 使用するフォームオブジェクトをリクエストスコープに格納する.
	 * 
	 * @return フォーム
	 */
	@ModelAttribute
	public UpdateEmployeeForm setUpForm() {
		return new UpdateEmployeeForm();
	}

	/////////////////////////////////////////////////////
	// ユースケース：従業員一覧を表示する
	/////////////////////////////////////////////////////
	/**
	 * 従業員一覧画面を出力します.
	 * 
	 * @param model モデル
	 * @return 従業員一覧画面
	 */
	@GetMapping("/showList")
	public String showList(Model model) {
		List<Employee> employeeList = employeeService.showList();
		model.addAttribute("employeeList", employeeList);
		return "employee/list";
	}

	/////////////////////////////////////////////////////
	// ユースケース：従業員詳細を表示する
	/////////////////////////////////////////////////////
	/**
	 * 従業員詳細画面を出力します.
	 * 
	 * @param id    リクエストパラメータで送られてくる従業員ID
	 * @param model モデル
	 * @return 従業員詳細画面
	 */
	@GetMapping("/showDetail")
	public String showDetail(String id, Model model) {
		Employee employee = employeeService.showDetail(Integer.parseInt(id));
		model.addAttribute("employee", employee);
		return "employee/detail";
	}

	/////////////////////////////////////////////////////
	// ユースケース：従業員詳細を更新する
	/////////////////////////////////////////////////////
	/**
	 * 従業員詳細(ここでは扶養人数のみ)を更新します.
	 * 
	 * @param form 従業員情報用フォーム
	 * @return 従業員一覧画面へリダクレクト
	 */
	@PostMapping("/update")
	public String update(@Validated UpdateEmployeeForm form, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return showDetail(form.getId(), model);
		}
		Employee employee = new Employee();
		employee.setId(form.getIntId());
		employee.setDependentsCount(form.getIntDependentsCount());
		employeeService.update(employee);
		return "redirect:/employee/showList";
	}

	/////////////////////////////////////////////////////
	// ユースケース：従業員一覧を曖昧検索で表示する
	/////////////////////////////////////////////////////
	/**
	 * 従業員一覧を曖昧検索で表示します.
	 * 
	 * @param model モデル
	 * @return 従業員一覧画面
	 */
	@GetMapping("/search")
	public String search(Model model, String name) {
		List<Employee> employeeList;
		if (name.isEmpty()) {
			employeeList = employeeService.showList();
		} else {
			employeeList = employeeService.searchName(name);
		}

		if (employeeList.isEmpty()) {
			model.addAttribute("searchMessage", "1件もありませんでした");
			employeeList = employeeService.showList();
		}

		model.addAttribute("employeeList", employeeList);
		model.addAttribute("name", name);
		return "employee/list";
	}

	/////////////////////////////////////////////////////
	// ユースケース：従業員登録画面を表示する
	/////////////////////////////////////////////////////
	/**
	 * 従業員を登録する画面を表示します.
	 * 
	 * @param model モデル
	 * @return 従業員登録画面
	 */
	@GetMapping("/insertForm")
	public String insertForm(Model model, InsertEmployeeForm insertEmployeeForm) {
		return "employee/insert";
	}

	/////////////////////////////////////////////////////
	// ユースケース：従業員を登録する
	/////////////////////////////////////////////////////
	/**
	 * 従業員を登録します
	 * 
	 * @param model モデル
	 * @return 従業員一覧画面
	 */
	@PostMapping("/insert")
	public synchronized String insert(@Validated InsertEmployeeForm insertEmployeeForm, BindingResult result, Model model)
			throws IOException {

		// 画像が選択されていない場合のチェック
		if (insertEmployeeForm.getImageData().isEmpty()) {
			// imageDataフィールドに対してエラーを追加
			result.rejectValue("imageData", "NotNull", "画像を選択してください");
		}
		// フォームの初期バリデーションチェック
		if (result.hasErrors()) {
			return insertForm(model, insertEmployeeForm);
		}

		Employee employee = new Employee();
		BeanUtils.copyProperties(insertEmployeeForm, employee);

		// 画像ファイルをディレクトリにアップロード
		// 乱数を生成するクラス
		Random random = new Random();
		// 0~99の乱数を生成
		int randomNumber = random.nextInt(100);
		// 画像ファイル名を生成
		String fileName = randomNumber + insertEmployeeForm.getImageData().getOriginalFilename();
		// 画像ファイルをディレクトリにアップロード
		Path dst = Path.of("src/main/resources/static/img", fileName);
		// 画像ファイルをコピー
		Files.copy(insertEmployeeForm.getImageData().getInputStream(), dst);
		employee.setImage(fileName);
		employee.setId(employeeService.findMaxId() + 1);
		employeeService.insert(employee);

		return "redirect:/employee/showList";
	}
}
