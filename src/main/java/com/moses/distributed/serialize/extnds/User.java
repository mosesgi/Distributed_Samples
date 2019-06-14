package com.moses.distributed.serialize.extnds;

import java.io.Serializable;

public class User extends SuperUser implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
    public String toString() {
        return "User{} " + super.toString();
    }
}
