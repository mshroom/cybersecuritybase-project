package sec.project.config;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import sec.project.domain.Account;
import sec.project.domain.Password;
import sec.project.repository.AccountRepository;
import sec.project.repository.PasswordDao;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private AccountRepository accountRepository;

    @PostConstruct
    public void init() throws ClassNotFoundException, SQLException {
        // Creating admin user with a nice password that is easy to remember
        Account account = new Account("admin", "admin");
        accountRepository.save(account);

        // Creating another user account
        account = new Account("donald", "duck");
        accountRepository.save(account);

        // Creating another user account
        account = new Account("mickey", "mouse");
        accountRepository.save(account);
        
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountRepository.findByUsername(username);
        if (account == null) {
            throw new UsernameNotFoundException("No such user: " + username);
        }

        return new org.springframework.security.core.userdetails.User(
                account.getUsername(),
                account.getPassword(),
                true,
                true,
                true,
                true,
                Arrays.asList(new SimpleGrantedAuthority("USER")));
    }
}
