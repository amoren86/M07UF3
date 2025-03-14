package cat.institutmarianao.spring.model;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "ranks")
public class Rank implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@NotNull
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@Column(name = "positive_votes")
	private int positive;

	@Column(name = "negative_votes")
	private int negative;

	@Column(name = "total")
	private int total;

	public int getPositive() {
		return positive;
	}

	public void setPositive(int positive) {
		this.positive = positive;
		total = positive - getNegative();
	}

	public int getNegative() {
		return negative;
	}

	public void setNegative(int negative) {
		this.negative = negative;
		total = getPositive() - negative;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer rankId) {
		this.id = rankId;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}
}