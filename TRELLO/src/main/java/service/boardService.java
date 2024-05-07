package service;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

import model.board;
import model.user;


@Path("/board")
@Stateless
public  class boardService {
		
	
	    @PersistenceContext
		private EntityManager entityManager;
		
	    
	    
		//@EJB
		private List<board> boards;
	
	    public boardService() {
	    	this.boards = new ArrayList<>();
		}
	    

		@POST
	    @Path("/create")
	    @Consumes(MediaType.APPLICATION_JSON)
		@Produces
	    public void createBoard(user user,int id , String name) {
			board board = new board(id, name);
	        board.addCollaborator(user);
	        
	        //another way
	        //boards.add(board);
	    	
	        entityManager.persist(board);
	    }

	    @GET
	    @Path("/all")
	    @Produces
	    @Consumes(MediaType.APPLICATION_JSON)
	    public List<board> getUserBoards(user user) {
	    	TypedQuery<board> query = entityManager.createQuery("SELECT b FROM board b WHERE b.collaborators = :user", board.class);
	        query.setParameter("user", user);
	        return query.getResultList();

	    	//another way
	        /*List<board> userBoards = new ArrayList<>();
	        for (board board : boards) {
	            if (board.getCollaborators().contains(user)) {
	                userBoards.add(board);
	            }
	        }
	        return userBoards;*/
	    }

	    @POST
	    @Path("/invite")
	    @Consumes(MediaType.APPLICATION_JSON)
	    @Produces
	    public void inviteUser(user user, board board) {
	    	board.getCollaborators().add(user);
	    	board.addCollaborator(user);

	        entityManager.merge(board);
	    }

	    @DELETE
	    @Path("/delete")
	    @Produces
	    @Consumes(MediaType.APPLICATION_JSON)
	    public void deleteBoard(user user, board board) {
	        if (board.getCollaborators().contains(user)) {
		    	//another way
	        	//boards.remove(board);
	        	entityManager.remove(board) ;
	        }
	    }
	}
