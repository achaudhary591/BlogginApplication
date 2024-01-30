package com.akshay.blog.entities;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "users")
@NoArgsConstructor
@Getter
@Setter
public class User implements UserDetails {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int userId;
	
	@Column(name = "user_name", nullable = false, length = 100)
	private String name;
	private String email;
	private String password;
	private String about;

	@OneToMany(mappedBy = "category", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<Post> posts = new HashSet<>();

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)  // Adjusted mappedBy to "user"
	private Set<Comment> comments = new HashSet<>();

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(
			name = "user_role",
			joinColumns = @JoinColumn(name = "user_id",referencedColumnName = "userId"),
			inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "roleId")
	)
	private Set<Role> roles = new HashSet<>();

	/**
	 * @return 
	 */
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<SimpleGrantedAuthority> authorities = this.roles.stream().map(role -> new SimpleGrantedAuthority(role.getRole())).toList();
		return authorities;
	}

	/**
	 * @return 
	 */
	@Override
	public String getUsername() {
		return this.email;
	}

	/**
	 * @return 
	 */
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	/**
	 * @return 
	 */
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	/**
	 * @return 
	 */
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	/**
	 * @return 
	 */
	@Override
	public boolean isEnabled() {
		return true;
	}
}
