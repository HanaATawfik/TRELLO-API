
 package Interface;

import java.util.List;

import model.board;
import model.user;

public class Main {
    public static void main(String[] args) {
        serviceInterface service = new serviceInterface();
        //is hana going to make it?
        // Testing user 
        System.out.println("Testing user functionalities:");

        System.out.println("Registering a new user");
        service.registerUser(1, "test@example.com", "password", "Test User");

        System.out.println("Logging in");
        boolean loginStatus = service.loginUser("test@example.com", "password");
        if (loginStatus) {
            System.out.println("Login successful");
        } else {
            System.out.println("Login failed");
        }

        System.out.println("Updating user profile");
        user updatedUser = new user(1, "Updated Name", "updated@example.com", "updatedPassword");
        service.updateProfile(updatedUser);

        // Testing board 
        System.out.println("\nTesting board functionalities:");

        user user = new user(1, "Test User", "test@example.com", "password");

        System.out.println("Creating a new board");
        service.createBoard(user, 1, "Test Board");

        System.out.println("Retrieving user boards");
        List<board> userBoards = service.getUserBoards(user);
        System.out.println("User Boards: " + userBoards);

        System.out.println("Inviting another user to the board...");
        user invitedUser = new user(2, "Invited User", "invited@example.com", "password");
        board userBoard = userBoards.get(0); //board 1
        service.inviteUser(invitedUser, userBoard);

        System.out.println("Retrieving updated user boards");
        userBoards = service.getUserBoards(user);
        System.out.println("User Boards: " + userBoards);

        System.out.println("Deleting the board");
        service.deleteBoard(user, userBoard);

        System.out.println("Retrieving user boards after deletion");
        userBoards = service.getUserBoards(user);
        System.out.println("User Boards: " + userBoards);
    }
}

