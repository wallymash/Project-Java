package bank.rest;

import bank.core.Bank;
import bank.core.User;
import java.time.LocalDateTime;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Spring controller.
 */
@RequestMapping("/bank")
@RestController
public class SpringController {
  @Autowired
  public BankService bankService;

  /**
   * A get method for getting the bank read from json file.
   *
   * @return An object of type bank.
   *
   */
  @GetMapping
  public Bank getBank() {
    return bankService.getBank();
  }

  @PostMapping
  public void registerUser(@RequestBody User user) throws Exception {
    bankService.registerUser(user);

  }

  @GetMapping(path = "/{SSN}")
  public Optional<User> getUserBysocialSecurityNumber(
      @PathVariable("SSN") Long socialSecurityNumber) {
    return bankService.getUserBySocialSecurityNumber(socialSecurityNumber);
  }

  @PutMapping(path = "/account/{accountNumber}/deposit/{amount}")
  public void deposit(@PathVariable long accountNumber,
      @PathVariable double amount) throws Exception {
    bankService.deposit(accountNumber, amount);
  }

  @PutMapping(path = "/account/{accountNumber}/withdraw/{amount}")
  public void withdraw(@PathVariable long accountNumber,
      @PathVariable double amount) throws Exception {
    bankService.withdraw(accountNumber, amount);
  }

  @PutMapping(path = "/from/{from}/to/{to}/transfer/{amount}")
  public void transfer(@PathVariable long from, @PathVariable long to,
      @PathVariable double amount) throws Exception {
    bankService.transfer(from, to, amount);
  }

  @DeleteMapping(path = "/user/{SSN}")
  public void deleteUserBySocialSecurityNumber(
      @PathVariable("SSN") Long socialSecurityNumber) throws Exception {
    bankService.deleteUser(socialSecurityNumber);
  }

  @PutMapping(path = "/addintrest/{accountNumber}")
  public void addInterest(@PathVariable long accountNumber) throws Exception {
    bankService.addInterest(accountNumber, LocalDateTime.now().getYear());
  }

}
