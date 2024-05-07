package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.ejb.Stateless;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Stateless
@Table(name = "lists")
public class list {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    @Column(unique = true)
    private String name;

    @ManyToOne
    @JoinColumn(name = "board_id")
    private board board;

    @OneToMany(mappedBy = "list", cascade = CascadeType.ALL)
    private Set<Card> cards;

    public list() {
        cards = new ArrayList<>();
    }

    public list(String name) {
        this.name = name;
        cards = new ArrayList<>();
    }

    // Getters and setters
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

    public board getBoard() {
        return board;
    }

    public void setBoard(board board) {
        this.board = board;
    }

    public Set<Card> getCards() {
        return cards;
    }

    public void setCards(Set<Card> cards) {
        this.cards = cards;
    }

    public void addCard(Card card) {
        cards.add(card);
        card.setList(this);
    }

    public void removeCard(Card card) {
        cards.remove(card);
        card.setList(null);
    }
}
