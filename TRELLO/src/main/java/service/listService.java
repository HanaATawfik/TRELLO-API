package service;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import model.board;
import model.list;

@Path("/list")
@Stateless
public class listService {

    @PersistenceContext
    private EntityManager entityManager;

    @POST
    @Path("/create")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createList(@Valid board board, @NotNull String name) {
    	try
    	{
    		   list list = new list(name);
               list.setBoard(board);
               entityManager.persist(list);
               return Response.status(Response.Status.CREATED).entity("List created successfully").build();

    	}
    	catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Failed to create list").build();
        }     
    }

    @DELETE
    @Path("/delete")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deleteList(list list) {
    	 try {
             entityManager.remove(entityManager.contains(list) ? list : entityManager.merge(list));
             return Response.status(Response.Status.OK).entity("List deleted successfully").build();
         } catch (Exception e) {
             return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Failed to delete list").build();
         }    }
    
    @GET
    @Path("/board")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getListsByBoard(@Valid board board) {
        try {
            TypedQuery<list> query = entityManager.createQuery("SELECT l FROM List l WHERE l.board = :board", list.class);
            query.setParameter("board", board);
            List<list> lists = query.getResultList();  //jackie check this!!
            return Response.status(Response.Status.OK).entity(lists).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Failed to retrieve lists").build();
        }
    }
}
