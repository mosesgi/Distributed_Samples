package com.moses.distributed.springCxf;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements IUserService {

	@Override
	public List<User> getUsers() {
		return Storage.users;
	}

	@Override
	public Response delete(int id) {
		Response response = new Response();
		response.setCode("00");
		response.setMsg("success");
		return response;
	}

	@Override
	public User getUser(int id) {
		return Storage.users.get(id);
	}

	@Override
	public Response insert(User user) {
		return null;
	}

	@Override
	public Response update(User user) {
		return null;
	}

}
