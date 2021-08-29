package com.eve_coding.eveshop.utils;

import com.eve_coding.eveshop.model.User;
import com.eve_coding.eveshop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class UserValidator implements Validator {
    @Autowired
    private UserService userService;

    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        User user = (User) o;


        if (user.getFirstName().length() < 6 || user.getFirstName().length() > 32 ) {
            errors.rejectValue("firstName", "Size.userForm.firstName","This field is required");
        }else{
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstName", "NotEmpty","This field is required");
        }

        if (user.getFirstName().length() < 6 || user.getFirstName().length() > 32 ) {
            errors.rejectValue("lastName", "Size.userForm.lastName","This field is required");
        }else {
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastName", "NotEmpty","This field is required");
        }
        if (userService.getUserByEmail(user.getEmail()) != null ) {
            errors.rejectValue("email", "Duplicate.userForm.email","Email address has been used");
        }else{
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "NotEmpty","This field is required");
        }


        if (user.getPassword().length() < 8 || user.getPassword().length() > 32 ) {
            errors.rejectValue("password", "Size.userForm.password","Password must be between 8 and 32 characters.");
        }else{
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty","This field is required");
        }


        if (!user.getPasswordConfirm().equals(user.getPassword()) ) {
            errors.rejectValue("passwordConfirm", "Diff.userForm.passwordConfirm","Passwords do not match");
        }else{
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "passwordConfirm", "NotEmpty","This field is required");
        }
    }
}
