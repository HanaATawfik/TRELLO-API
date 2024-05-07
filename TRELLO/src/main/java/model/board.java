package model;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.JoinColumn;

@Entity
@Stateless

public class board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique = true)
    private String name;
    
    @ManyToMany
    @JoinTable(
            name = "board_user",
            joinColumns = @JoinColumn(name = "id") 
    )
    private List<user> collaborators = new ArrayList<>();
    
    
    public board() {
    } 
    
    public board(int id, String name) {
		this.id = id;
		this.name = name;
		this.collaborators = new ArrayList<>();
	}
    
    public void addCollaborator(user user) {
        collaborators.add(user);
    }
    
    public List<user> getCollaborators() {
        return collaborators;
    }

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

    
}