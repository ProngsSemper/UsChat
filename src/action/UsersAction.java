package action;

import dao.UsersDao;
import model.Users;

import java.util.List;

public class UsersAction {
    public static void main(String[] args) throws Exception {
//        UsersDao usersDao = new UsersDao();
//        List<Users> usersList = usersDao.query();
//        for (Users users : usersList){
//            System.out.println(users.getUsername()+","+users.getPassword());
//    }
//            UsersDao ud = new UsersDao();
//            Users users = new Users();
//            users.setUsername("爸爸");
//            users.setPassword("abx548");
//            ud.addUser(users);

        UsersDao usersDao = new UsersDao();
        List<Users> result = usersDao.query("a");
//        for (int i = 0; i < result.size(); i++) {
        System.out.println(result.get(0).toString());
//        }
    }
}
