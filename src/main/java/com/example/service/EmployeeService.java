package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.Employee;
import com.example.paging.Paging;
import com.example.repository.EmployeeRepository;

/**
 * 従業員情報を操作するサービス.
 * 
 * @author igamasayuki
 *
 */
@Service
@Transactional
public class EmployeeService {

	@Autowired
	private EmployeeRepository employeeRepository;

	/**
	 * 従業員情報を全件取得します.
	 * 
	 * @return 従業員情報一覧
	 */
	public List<Employee> showList() {
		List<Employee> employeeList = employeeRepository.findAll();
		return employeeList;
	}

	/**
	 * ページングと曖昧検索された従業員一覧情報を取得します
	 * @param paging ページング情報
	 * @param name 曖昧検索用の名前
	 * 
	 * @return 従業員一覧情報
	 */

	public List<Employee> findByNameWithPaging(Paging paging, String name) {
		List <Employee> employeeList = employeeRepository.findByNameWithPaging(paging, name);
		return employeeList;
	}

	/**
	 * 従業員情報を取得します.
	 * 
	 * @param id ID
	 * @return 従業員情報
	 * @throws org.springframework.dao.DataAccessException 検索されない場合は例外が発生します
	 */
	public Employee showDetail(Integer id) {
		Employee employee = employeeRepository.load(id);
		return employee;
	}

	/**
	 * 従業員情報を更新します.
	 * 
	 * @param employee 更新した従業員情報
	 */
	public void update(Employee employee) {
		employeeRepository.update(employee);
	}

	/**
	 * 従業員情報を曖昧検索します.
	 * 
	 * @return 従業員情報一覧
	 */
	public List<Employee> searchName(String name) {
		return employeeRepository.findByName(name);
	}

	/**
	 * 従業員情報を登録します.
	 * 
	 * @param employee 登録する従業員情報
	 */
	public void insert(Employee employee) {
		employeeRepository.insert(employee);
	}

	/**
	 * 従業員のIDの最大値を取得します.
	 */
	public Integer findMaxId() {
		return employeeRepository.findMaxId();
	}

	/**
	 * レコード数を取得します.
	 */
	public int totalRecord(String name) {
		return employeeRepository.totalRecord(name);
	}
}
