package security;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.freelapp.model.Roles;
import com.freelapp.model.User;


public class DatabaseUserDetails implements UserDetails{

	//private final Long id;
	private final String username;
	private final String password;
	private final Set<GrantedAuthority> authorities;
	private int id;
	
	public DatabaseUserDetails(User user) {
		this.id = user.getId();
		this.username = user.getEmail();
		this.password = user.getPassword();
		this.authorities = new HashSet<GrantedAuthority>();
		for (Roles ruolo : user.getRoles()) {
			this.authorities.add(new SimpleGrantedAuthority(ruolo.getNomeRuolo()));
		}
		
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {

		return this.authorities;
	}

	@Override
	public String getPassword() {

		return this.password;
	}

	@Override
	public String getUsername() {
		return this.username;
	}

}
