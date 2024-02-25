package com.example.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.domain.Employee;
import com.example.paging.Paging;

/**
 * employeesテーブルを操作するリポジトリ.
 * 
 * @author igamasayuki
 * 
 */
@Repository
public class EmployeeRepository {

	/**
	 * Employeeオブジェクトを生成するローマッパー.
	 */
	private static final RowMapper<Employee> EMPLOYEE_ROW_MAPPER = (rs, i) -> {
		Employee employee = new Employee();
		employee.setId(rs.getInt("id"));
		employee.setName(rs.getString("name"));
		employee.setImage(rs.getString("image"));
		employee.setGender(rs.getString("gender"));
		employee.setHireDate(rs.getDate("hire_date"));
		employee.setMailAddress(rs.getString("mail_address"));
		employee.setZipCode(rs.getString("zip_code"));
		employee.setAddress(rs.getString("address"));
		employee.setTelephone(rs.getString("telephone"));
		employee.setSalary(rs.getInt("salary"));
		employee.setCharacteristics(rs.getString("characteristics"));
		employee.setDependentsCount(rs.getInt("dependents_count"));
		return employee;
	};

	@Autowired
	private NamedParameterJdbcTemplate template;

	/**
	 * 従業員一覧情報を入社日順で取得します.
	 * 
	 * @return 全従業員一覧 従業員が存在しない場合はサイズ0件の従業員一覧を返しま
	 */
	public List<Employee> findAll() {
		String sql = "SELECT id,name,image,gender,hire_date,mail_address,zip_code,address,telephone,salary,characteristics,dependents_count FROM employees ORDER BY hire_date";
		List<Employee> employeeList = template.query(sql, EMPLOYEE_ROW_MAPPER);
		return employeeList;
	}

	/**
	 * ページングのされた従業員一覧情報を取得します.
	 * @param paging ページング情報
	 * @param name 曖昧検索用の名前
	 * 
	 * @return ページングされた従業員一覧
	 */
	public List<Employee> findByNameWithPaging(Paging paging, String name) {

		// SQL文を作成
		StringBuilder sql = new StringBuilder();
		sql .append("SELECT id,name,image,gender,hire_date,mail_address,zip_code,address,telephone,salary,characteristics,dependents_count FROM employees ");

		MapSqlParameterSource param = new MapSqlParameterSource();

		//曖昧検索用の名前が指定されている場合はWHERE句を追加
		if (name != null && !name.isEmpty()) {
			sql.append("WHERE name LIKE :name ");
			param.addValue("name", "%" + name + "%");
		}

		sql.append("ORDER BY hire_date LIMIT :limit OFFSET :offset");
		param.addValue("limit", paging.getPageSize());
		param.addValue("offset", (paging.getNowPage() - 1) * paging.getPageSize());

		List<Employee> employeeList = template.query(sql.toString(), param, EMPLOYEE_ROW_MAPPER);

		if(employeeList.size() == 0) {
			return null;
		} else {
			return employeeList;
		}
	}

	/**
	 * 主キーから従業員情報を取得します.
	 * 
	 * @param id 検索したい従業員ID
	 * @return 検索された従業員情報
	 * @exception org.springframework.dao.DataAccessException 従業員が存在しない場合は例外を発生します
	 */
	public Employee load(Integer id) {
		String sql = "SELECT id,name,image,gender,hire_date,mail_address,zip_code,address,telephone,salary,characteristics,dependents_count FROM employees WHERE id=:id";

		SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);

		Employee development = template.queryForObject(sql, param, EMPLOYEE_ROW_MAPPER);

		return development;
	}

	/**
	 * 従業員情報を変更します.
	 */
	public void update(Employee employee) {
		SqlParameterSource param = new BeanPropertySqlParameterSource(employee);

		String updateSql = "UPDATE employees SET dependents_count=:dependentsCount WHERE id=:id";
		template.update(updateSql, param);
	}

	/**
	 * 従業員一覧情報を曖昧検索で取得します.
	 * 
	 * @return 全従業員一覧 従業員が存在しない場合はサイズ0件の従業員一覧を返します
	 */
	public List<Employee> findByName(String name) {
		String sql = "SELECT id,name,image,gender,hire_date,mail_address,zip_code,address,telephone,salary,characteristics,dependents_count FROM employees WHERE name LIKE :name ORDER BY hire_date";
		SqlParameterSource param = new MapSqlParameterSource().addValue("name", "%" + name + "%");

		List<Employee> developmentList = template.query(sql, param, EMPLOYEE_ROW_MAPPER);

		return developmentList;
	}

	/**
	 * 従業員情報を登録します.
	 */
	public void insert(Employee employee) {
		SqlParameterSource param = new BeanPropertySqlParameterSource(employee);
		String sql = """
				INSERT INTO employees(id,name,image,gender,hire_date,mail_address,zip_code,address,telephone,salary,characteristics,dependents_count) values(:id, :name, :image, :gender, :hireDate, :mailAddress, :zipCode, :address, :telephone, :salary, :characteristics, :dependentsCount);
					""";
		template.update(sql, param);
	}

	/**
	 * 従業員IDの最大値を取得します.
	 */
	public Integer findMaxId() {
		String sql = "SELECT MAX(id) FROM employees";
		SqlParameterSource param = new MapSqlParameterSource();
		Integer maxId = template.queryForObject(sql, param, Integer.class);
		return maxId;
	}

	/**
	 * ページングのためのレコード数を取得します.
	 */
	public int totalRecord(String name) {
		// SQL文を作成
		StringBuilder sql = new StringBuilder();
		// レコード数を取得するSQL文
		sql.append("SELECT COUNT(*) FROM employees ");
		// 検索条件を設定
		MapSqlParameterSource param = new MapSqlParameterSource();
		if (name != null && !name.isEmpty()) {
			sql.append("WHERE name LIKE :name ");
			param.addValue("name", "%" + name + "%");
		}
		// SQL実行
		Integer count = template.queryForObject(sql.toString(), param, Integer.class);
		// 結果を返却
		return count;
	}
}
