package com.tfkfan.server.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.tfkfan.hibernate.dao.RoleDao;
import com.tfkfan.hibernate.dao.UserDao;
import com.tfkfan.hibernate.entities.Role;
import com.tfkfan.hibernate.entities.User;
import com.tfkfan.security.enums.UserRole;
import com.tfkfan.server.service.dto.UserDto;

@Service("userService")
public class UserService extends AbstractService<User> {

	@Autowired
	PasswordEncoder pe;

	@Autowired
	RoleService roleService;

	Role userRole;

	@Autowired
	public void initRole() {
		userRole = roleService.getByRole(UserRole.ROLE_USER.getRole());
	}

	public UserService() {

	}

	public User getUserByUsername(String username) {
		return ((UserDao) getDao()).findByUsername(username);
	}

	public void createNewUser(UserDto userDto) throws Exception {
		User user = new User();

		String password = pe.encode(userDto.getPassword());

		user.setUsername(userDto.getUsername());
		user.setPassword(password);
		user.setRole(userRole);

		((UserDao) getDao()).createUser(user);
	}

	public List<UserDto> listAllUserDTO() throws Exception {
		List<User> usrs = getAll();
		List<UserDto> resp = new ArrayList<UserDto>();
		for (User u : usrs)
			resp.add(new UserDto(u.getId(), u.getUsername(), u.getPassword(), u.getRole().getRole()));
		return resp;
	}

	public void updateUser(UserDto userDto) throws Exception {
		User user = get(userDto.getId());
		if (user != null) {
			user.setUsername(userDto.getUsername());
			if (!userDto.getPassword().isEmpty())
				user.setPassword(pe.encode(userDto.getPassword()));
			if (!userDto.getRole().isEmpty()) {
				Role role = roleService.getByRole(userDto.getRole());
				if (role != null)
					user.setRole(role);
			}
			update(user);
		}
	}
}
