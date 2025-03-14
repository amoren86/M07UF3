package cat.institutmarianao.spring.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "users")
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@NotBlank
	@Size(max = 30)
	@Column(name = "username")
	private String username;

	@NotBlank
	@Size(max = 20)
	@Column(name = "name")
	private String name;

	@NotBlank
	@Size(max = 50)
	@Column(name = "email")
	private String email;

	@NotBlank
	@Size(min = 5, max = 50)
	@Column(name = "password")
	private String password;

	@NotNull
	@Column(name = "rank")
	private Integer rank;

	@NotNull
	@Column(name = "active")
	private Boolean active;

	@NotNull
	@Column(name = "created_on")
	private Timestamp createdOn;

	@OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.EAGER)
	private List<Question> questions;

	@OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.EAGER)
	private List<Answer> answers;

	@OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.EAGER)
	private List<Vote> votes;

	public Long getId() {
		return id;
	}

	public void setId(Long userId) {
		id = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getRank() {
		return rank;
	}

	public void setRank(Integer rank) {
		this.rank = rank;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public Timestamp getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Timestamp createdOn) {
		this.createdOn = createdOn;
	}

	public List<Question> getQuestions() {
		return questions;
	}

	public void setQuestions(List<Question> questions) {
		this.questions = questions;
	}

	public List<Answer> getAnswers() {
		return answers;
	}

	public void setAnswers(List<Answer> answers) {
		this.answers = answers;
	}

	public List<Vote> getVotes() {
		return votes;
	}

	public void setVotes(List<Vote> votes) {
		this.votes = votes;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof User other)) {
			return false;
		}
		return id == other.id;
	}
}
