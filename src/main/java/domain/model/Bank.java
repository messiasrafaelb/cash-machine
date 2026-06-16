package domain.model;

import com.opencsv.bean.CsvBindByName;

public class Bank {

	@CsvBindByName(column = "id")
	private Integer id;

	@CsvBindByName(column = "name")
	private String name;

	public Bank() {
	}

	public Integer getId() {
		return id;
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

	@Override
	public String toString() {
		return """
				========================================
				DADOS DO BANCO
				========================================
				🆔 ID: %d
				🏦 Nome: %s
				========================================
				"""
				.formatted(id, name);
	}

}
