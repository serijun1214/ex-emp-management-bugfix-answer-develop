package com.example.form;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * 従業員情報登録時に使用するフォーム. 
 */
public class InsertEmployeeForm {
	/** id */
	private Integer id;
	/** 従業員名 */
	@NotBlank(message = "名前を入力してください")
	private String name;
	/** 画像 */
	@NotNull(message = "画像を選択してください")
	private MultipartFile imageData;
	/** 性別 */
	@NotBlank(message = "性別を選択してください")
	private String gender;
	/** 入社日 */
	@NotNull(message = "入社日を入力してください")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date hireDate;
	/** メールアドレス */
	@NotBlank(message = "メールアドレスを入力してください")
	private String mailAddress;
	/** 郵便番号 */
	@NotBlank(message = "郵便番号を入力してください")
	private String zipCode;
	/** 住所 */
	@NotBlank(message = "住所を入力してください")
	private String address;
	/** 電話番号 */
	@NotBlank(message = "電話番号を入力してください")
	private String telephone;
	/** 給料 */
	@NotNull(message = "給料を入力してください")
	@Min(value = 0, message = "給料は0以上で入力してください")
	private Integer salary;
	/** 特性 */
	@NotBlank(message = "特性を入力してください")
	private String characteristics;
	/** 扶養人数 */
	@NotNull(message = "扶養人数を入力してください")
	private Integer dependentsCount;

	/**
	 * 引数無しのコンストラクタ.
	 */
	public InsertEmployeeForm() {
	}

	/**
	 * 初期化用コンストラクタ.
	 * 
	 * @param id              ID
	 * @param name            従業員名
	 * @param imageData       画像
	 * @param gender          性別
	 * @param hireDate        入社日
	 * @param mailAddress     メールアドレス
	 * @param zipCode         郵便番号
	 * @param address         住所
	 * @param telephone       電話番号
	 * @param salary          給料
	 * @param characteristics 特性
	 * @param dependentsCount 扶養人数
	 */
	

	public Integer getId() {
		return id;
	}

	public InsertEmployeeForm(Integer id, String name, MultipartFile imageData, String gender, Date hireDate,
			String mailAddress, String zipCode, String address, String telephone, Integer salary, String characteristics,
			Integer dependentsCount) {
		this.id = id;
		this.name = name;
		this.imageData = imageData;
		this.gender = gender;
		this.hireDate = hireDate;
		this.mailAddress = mailAddress;
		this.zipCode = zipCode;
		this.address = address;
		this.telephone = telephone;
		this.salary = salary;
		this.characteristics = characteristics;
		this.dependentsCount = dependentsCount;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public MultipartFile getImageData() {
		return imageData;
	}

	public void setImageData(MultipartFile imageData) {
		this.imageData = imageData;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Date getHireDate() {
		return hireDate;
	}

	public void setHireDate(Date hireDate) {
		this.hireDate = hireDate;
	}

	public String getMailAddress() {
		return mailAddress;
	}

	public void setMailAddress(String mailAddress) {
		this.mailAddress = mailAddress;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public Integer getSalary() {
		return salary;
	}

	public void setSalary(Integer salary) {
		this.salary = salary;
	}

	public String getCharacteristics() {
		return characteristics;
	}

	public void setCharacteristics(String characteristics) {
		this.characteristics = characteristics;
	}

	public Integer getDependentsCount() {
		return dependentsCount;
	}

	public void setDependentsCount(Integer dependentsCount) {
		this.dependentsCount = dependentsCount;
	}

	@Override
	public String toString() {
		return "InsertEmployeeForm [id=" + id + ", name=" + name + ", imageData=" + imageData + ", gender=" + gender
				+ ", hireDate=" + hireDate + ", mailAddress=" + mailAddress + ", zipCode=" + zipCode + ", address=" + address
				+ ", telephone=" + telephone + ", salary=" + salary + ", characteristics=" + characteristics
				+ ", dependentsCount=" + dependentsCount + "]";
	}

	
}
