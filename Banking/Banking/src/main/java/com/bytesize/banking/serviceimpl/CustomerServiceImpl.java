package com.bytesize.banking.serviceimpl;

import com.bytesize.banking.entity.Account;
import com.bytesize.banking.entity.Customer;
import com.bytesize.banking.exceptions.AlreadyExistsException;
import com.bytesize.banking.exceptions.NotFoundException;
import com.bytesize.banking.repository.CustomerRepo;
import com.bytesize.banking.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService, UserDetailsService {

    private final CustomerRepo customerRepo;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public CustomerServiceImpl(CustomerRepo  customerRepoI, PasswordEncoder encoder)
    {
        this.customerRepo = customerRepoI;
        this.passwordEncoder = encoder;
    }

    public Customer findCustomerByUserName(String userName)
    {
        return customerRepo.findByUserName(userName)
                .orElseThrow(()-> new NotFoundException("Not an existing customer!"));
    }

    public Customer findCustomerByCustomerName(String customerName)
    {
        return customerRepo.findByCustomerName(customerName)
                .orElseThrow(()->new NotFoundException("Not an existing customer!"));
    }


    public void registerCustomer(String customerName, String userName, String password, Account account)
    {

        if(customerRepo.findByUserName(userName).isPresent())
        {
            throw new AlreadyExistsException("Customer already exists.");
        }

        Customer customer = new Customer();

        customer.setUserName(userName);
        customer.setPassword(passwordEncoder.encode(password));
        customer.setCustomerName(customerName);
        customer.setAcccount(List.of(account));

        customerRepo.save(customer);
    }

    public boolean updateCustomerAccounts(long customerId, List<Account> accounts)
    {
        Optional<Customer> oldCustomer = customerRepo.findByCustomerId(customerId);
        if(oldCustomer.isEmpty())
        {
            throw new NotFoundException("Invalid Customer Id");

        }
        Customer customer = oldCustomer.get();
        customer.setAcccount(accounts);
        customerRepo.save(customer);
        return true;
    }

    public boolean updateCredential(String userName, String newThing, boolean isUserName)
    {
        Optional<Customer> oldCustomer = customerRepo.findByUserName(userName);

        if(oldCustomer.isPresent())
        {
            Customer customer = oldCustomer.get();
            if(isUserName)customer.setUserName(newThing);
            else customer.setPassword(newThing);

            customerRepo.save(customer);

            return true;
        }
        return false;
    }

    public List<Account> getCustomerAccountsByCustomerId(long customerId)
    {
        Optional<Customer> customer = customerRepo.findByCustomerId(customerId);
        if(customer.isEmpty())
        {
            throw new NotFoundException("Invalid Customer Id : " + customerId);
        }
        return customer.get().getAcccount();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Customer customer = findCustomerByUserName(username);
        if(customer == null){
            throw new UsernameNotFoundException("Invalid UserName/Password");
        }
        customer.setAuthorities(authorities());
        return customer;
    }

    public Collection<? extends GrantedAuthority> authorities() {
        return Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"));
    }

    
}
