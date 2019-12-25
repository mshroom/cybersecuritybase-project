package sec.project.config;

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
import sec.project.repository.PasswordRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private AccountRepository accountRepository;
    
    @Autowired
    private PasswordRepository messageRepository;

    @PostConstruct
    public void init() {
        // Creating admin user with a nice password that is easy to remember
        Account account = new Account("admin", "admin");
        accountRepository.save(account);

        // Creating another user account
        account = new Account("donald", "duck");
        accountRepository.save(account);

        // Creating another user account
        account = new Account("mickey", "mouse");
        accountRepository.save(account);
        
        // Creating passwords
        Password password = new Password("gmail", "mickey", "annoyingmouse");
        password.setAccount(account);
        messageRepository.save(password);
        password = new Password("facebook", "mickey@gmail.com", "imsoclever");
        password.setAccount(account);
        messageRepository.save(password);
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
