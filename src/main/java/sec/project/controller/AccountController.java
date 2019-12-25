package sec.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import sec.project.domain.Account;
import sec.project.domain.Password;
import sec.project.repository.AccountRepository;
import sec.project.repository.PasswordRepository;

@Controller
public class AccountController {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private PasswordRepository passwordRepository;

    @RequestMapping("*")
    public String defaultMapping() {
        return "redirect:/login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loadForm() {
        return "login";
    }

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public String list(Model model) {
        model.addAttribute("users", accountRepository.findAll());
        return "users";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String submitForm(@RequestParam String username, @RequestParam String password) {
        Account account = accountRepository.findByUsername(username);
        if (account == null || !account.getPassword().equals(password)) {
            return "redirect:/login";
        } else {
            return "redirect:/home/" + account.getId();
        }
    }

    @RequestMapping(value = "/home/{id}", method = RequestMethod.GET)
    public String home(Model model, @PathVariable Long id) {
        Account account = accountRepository.findOne(id);
        model.addAttribute("user", account);
        return "home";
    }

    @RequestMapping(value = "/passwords", method = RequestMethod.GET)
    public String passwordLogin(Model model) {
        return "passwordlogin";
    }

    @RequestMapping(value = "/passwords", method = RequestMethod.POST)
    public String showPasswords(@RequestParam String username, @RequestParam String password) {
        Account account = accountRepository.findByUsername(username);
        if (account == null || !account.getPassword().equals(password)) {
            return "redirect:/passwords";
        } else {
            return "redirect:/passwords/" + account.getId();
        }
    }

    @RequestMapping(value = "/passwords/{id}", method = RequestMethod.GET)
    public String passwords(Model model, @PathVariable Long id) {
        Account account = accountRepository.findOne(id);
        model.addAttribute("passwords", passwordRepository.findAll());
        model.addAttribute("user", account);
        return "passwords";
    }

    @RequestMapping(value = "/passwords/{id}", method = RequestMethod.POST)
    public String savePassword(@PathVariable Long id, @RequestParam String service, @RequestParam String username, @RequestParam String password) {
        Password newPassword = new Password(service, username, password);
        newPassword.setAccount(accountRepository.findOne(id));
        passwordRepository.save(newPassword);
        return "redirect:/passwords/" + id;
    }

}
