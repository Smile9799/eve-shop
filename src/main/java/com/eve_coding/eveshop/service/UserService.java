package com.eve_coding.eveshop.service;

import com.eve_coding.eveshop.model.Role;
import com.eve_coding.eveshop.model.ShoppingCart;
import com.eve_coding.eveshop.model.User;
import com.eve_coding.eveshop.model.UserShippingAddress;
import com.eve_coding.eveshop.repository.RoleRepository;
import com.eve_coding.eveshop.repository.UserRepository;
import com.eve_coding.eveshop.repository.UserShippingAddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private UserShippingAddressRepository userShippingAddressRepository;

    public void saveUser(User user){

        // TODO: only get appropriate roles
        Role role = roleRepository.getRoleByRoleName("USER");

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        //assign the newly registered user some roles
        Set<Role> roles1 = new HashSet<>();
        roles1.add(role);
        user.setRoles(roles1);

        //immediately bind the user to the shopping cart
        ShoppingCart shoppingCart = new ShoppingCart();
        user.setShoppingCart(shoppingCart);
        shoppingCart.setUser(user);

        userRepository.save(user);
    }

    public User getUserByEmail(String email){
        return userRepository.getUserByEmail(email);
    }

    public void saveUserShippingAddress(UserShippingAddress userShippingAddress,User user){
        userShippingAddress.setUser(user);
        userShippingAddressRepository.save(userShippingAddress);
    }

    public UserShippingAddress userShippingAddress(Long userShippingAddressId){
        return userShippingAddressRepository.getById(userShippingAddressId);
    }

    public void deleteUserShippingAddress(UserShippingAddress shippingAddress){
        userShippingAddressRepository.delete(shippingAddress);
    }

}
