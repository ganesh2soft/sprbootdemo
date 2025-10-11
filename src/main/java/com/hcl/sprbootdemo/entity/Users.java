package com.hcl.sprbootdemo.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.Objects;
import java.util.Set;

import org.hibernate.annotations.ColumnDefault;



@Entity

@Table(name = "users", uniqueConstraints = { @UniqueConstraint(columnNames = "username"),
		@UniqueConstraint(columnNames = "email") })
public class Users {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private Long userId;

	@NotBlank
	@Size(max = 20)
	@Column(name = "username")
	private String userName;

	@NotBlank
	@Size(max = 50)
	@Email
	@Column(name = "email")
	private String email;

	@NotBlank
	@Size(max = 120)
	@Column(name = "password")
	private String password;
	
	@Column(nullable = false)
	@ColumnDefault("'ROLE_USER'")
	private String roles = "ROLE_USER";

	private String address;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
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

	public String getRoles() {
		return roles;
	}

	public void setRoles(String roles) {
		this.roles = roles;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Override
	public int hashCode() {
		return Objects.hash(address, email, password, roles, userId, userName);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Users other = (Users) obj;
		return Objects.equals(address, other.address) && Objects.equals(email, other.email)
				&& Objects.equals(password, other.password) && Objects.equals(roles, other.roles)
				&& Objects.equals(userId, other.userId) && Objects.equals(userName, other.userName);
	}

	@Override
	public String toString() {
		return "Users [userId=" + userId + ", userName=" + userName + ", email=" + email + ", password=" + password
				+ ", roles=" + roles + ", address=" + address + "]";
	}

	public Users(Long userId, @NotBlank @Size(max = 20) String userName, @NotBlank @Size(max = 50) @Email String email,
			@NotBlank @Size(max = 120) String password, String roles, String address) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.email = email;
		this.password = password;
		this.roles = roles;
		this.address = address;
	}

	public Users() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	
	
	
	
	
	
	
	
	
//	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.EAGER)
//	@JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
//	private Set<AppRole> roles = new HashSet<>();
	

//	@OneToMany(mappedBy = "user", cascade = { CascadeType.PERSIST, CascadeType.MERGE }, orphanRemoval = true)
//    @JoinTable(name = "user_address",
//                joinColumns = @JoinColumn(name = "user_id"),
//                inverseJoinColumns = @JoinColumn(name = "address_id"))
//	private List<String> addresses = new ArrayList<>();
	
	
//	@OneToOne(mappedBy = "users", cascade = { CascadeType.PERSIST, CascadeType.MERGE }, orphanRemoval = true)
//	private Cart cart;
//
//	
//	@OneToMany(mappedBy = "users", cascade = { CascadeType.PERSIST, CascadeType.MERGE }, orphanRemoval = true)
//	private Set<Product> products;
//	
	
	
	
	
	
	
}