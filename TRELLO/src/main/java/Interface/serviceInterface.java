
 package Interface;

import java.util.List;
import javax.ejb.EJB;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import model.board;
import model.user;
import service.userService;
import service.boardService;

@ApplicationPath("/api")
public class serviceInterface extends Application {

    @PersistenceContext //(unitName="hello")
    private EntityManager entityManager;

    private userService userService;
    private boardService boardService;

    public serviceInterface() {
        this.userService = new userService();
        this.boardService = new boardService();
    }
    
    
    // User methods
    public void registerUser(int id, String email, String password, String name) {
        userService.register(id, email, password, name);
    }

    public boolean loginUser(String email, String password) {
        return userService.login(email, password);
    }

    public void updateProfile(user updatedUser) {
        userService.updateProfile(updatedUser);
    }

    // Board methods
    public void createBoard(user user, int id, String name) {
        boardService.createBoard(user, id, name);
    }

    public List<board> getUserBoards(user user) {
        return boardService.getUserBoards(user);
    }

    public void inviteUser(user user, board board) {
        boardService.inviteUser(user, board);
    }

    public void deleteBoard(user user, board board) {
        boardService.deleteBoard(user, board);
    }
}
