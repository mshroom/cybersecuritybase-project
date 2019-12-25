package sec.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import sec.project.domain.Account;
import sec.project.repository.AccountRepository;

@Controller
public class AccountController {

    @Autowired
    private AccountRepository accountRepository;

    @RequestMapping("*")
    public String defaultMapping() {
        return "redirect:/login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loadForm() {
        return "login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String submitForm(@RequestParam String username, @RequestParam String password) {
        Account account = accountRepository.findByUsername(username);
        if (account == null || !account.getPassword().equals(password)) {
            return "redirect:/login";
        } else {
            return "done";
        }
    }

}
