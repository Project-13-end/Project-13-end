package com.ll.project_13_backend.test_security.prinipal;

import org.springframework.security.core.userdetails.User;

public class EmptyUser extends User {
    public EmptyUser(){
        super(null,null,null);
    }
}
