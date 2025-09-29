package Security;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.freelapp.model.Roles;
import com.freelapp.model.User;
import com.freelapp.model.User.Role;

public class DatabaseUserDetails implements UserDetails{

	private final Long id;
	private final String username;
	private final String password;
	private final Set<GrantedAuthority> authorities;
	
	public DatabaseUserDetails(User utente) {
		this.id = utente.getId();
		this.username = utente.getEmail();
		this.password = utente.getPassword();
		this.authorities = new HashSet<GrantedAuthority>();
		for(Roles ruolo : utente.getRuolo()) {
			this.authorities.add(new SimpleGrantedAuthority(ruolo.getRuolo()));
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
	
	public long getId() {
		
		return id;
	}

}
