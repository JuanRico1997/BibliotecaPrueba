package org.example.services;

import org.example.dao.RolDao;
import org.example.dao.UserDao;
import org.example.domain.Partner;
import org.example.domain.Rol;
import org.example.domain.User;
import org.example.domain.Users;

import java.util.List;

public class UserService {
    private final UserDao userDao;
    private final RolDao rolDao;

    public UserService(UserDao userDao, RolDao rolDao) {
        this.userDao = userDao;
        this.rolDao = rolDao;
    }

    public boolean registerUser(String name, String email, String password, String roleName) {

        // 1️⃣ Verificar si el usuario ya existe
        if (userDao.findByEmail(email) != null) {
            System.out.println("El usuario ya existe");
            return false;
        }
        // 2️⃣ Buscar el rol en la base de datos
        Rol rol = rolDao.findByName(roleName);
        if (rol == null) {
            System.out.println("El rol '" + roleName + "' no existe en la base de datos");
            return false;
        }
        // 3️⃣ Crear el objeto Users o Partner según el rol
        Users user;
        if (rol.getName().equalsIgnoreCase("Partner")) {
            user = new Partner(name, email, password, rol);
        } else {
            user = new User(name, email, password, rol);
        }
        // 4️⃣ Guardar en la base de datos
        return userDao.createUser(user);
    }

    public Users login(String email, String password) {
        Users user = userDao.findByEmail(email);
        if (user == null) {
            System.out.println("El usuario no existe");
            return null;
        }
        if (!user.getPassword().equals(password)) {
            System.out.println("La contraseña es incorrecta");
            return null;
        }
        return user;
    }

    public List<Users> listAllUsers() {
        return userDao.findAllUsers();
    }
}