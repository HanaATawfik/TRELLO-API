package service;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.enterprise.inject.Produces;
import javax.faces.application.Application;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import model.user;

@Path("/user")
@Stateless
public class userService {
	
	    @PersistenceContext
		private EntityManager entityManager;
		
	    
	    //@EJB
		private List<user> users;
		
	    public userService() {
	    	this.users = new ArrayList<>();
		}

		@POST
	    @Path("/register")
		@Consumes(MediaType.APPLICATION_JSON)
		@Produces
	    public void register(int id ,String email, String password, String name) {
			user user = new user(id, email, password, name);
			
			//another way
			//users.add(user);
	    	
	        entityManager.persist(user);
	        
	    }
		

	    @POST
	    @Path("/login")
	    @Consumes(MediaType.APPLICATION_JSON)
	    @Produces
	    public boolean login(String email, String password) {
	    	TypedQuery<user> query = entityManager.createQuery("SELECT u FROM user u WHERE u.email = :email AND u.password = :password", user.class);
	        query.setParameter("email", email);
	        query.setParameter("password", password);
	        List<user> users = query.getResultList();
	        return !users.isEmpty();
	    	
	    	//another way
	    	/*for (user user : users) {
	            if (user.getEmail().equals(email) && user.getPassword().equals(password)) {
	                return true;
	            }
	        }
	        return false;*/
	    }

	    @PUT
	    @Path("/profile")
	    @Consumes(MediaType.APPLICATION_JSON)
	    @Produces
    	public void updateProfile(user updatedUser) {
	    	entityManager.merge(updatedUser);

	    	//another way
            /*for (user user : users) {
                if (user.getId() == updatedUser.getId()) {
                    user.setName(updatedUser.getName());
                    user.setEmail(updatedUser.getEmail());
                    user.setPassword(updatedUser.getPassword());
                    break;
                }
            }*/
        }
	}
