package deeroot.deeroot_shop.domain.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;


    @Builder.Default
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_owned_items", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "music_item_id", referencedColumnName = "id"))
    private Set<MusicItem> ownedMusicItems = new HashSet<>();

    @Builder.Default
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private Set<Role> roles = new HashSet<>();


    @Builder.Default
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_shopping_cart", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "music_item_id", referencedColumnName = "id"))
    private Set<MusicItem> shoppingCart = new HashSet<>();

    @Builder.Default
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_purchased_items", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "music_item_id", referencedColumnName = "id"))
    private Set<MusicItem> purchasedItems = new HashSet<>();
    //For mid-transaction logic


    public String toString(){


        String owned = "\n\n\nOwned Items:\n\n";
        for (MusicItem item : ownedMusicItems){
            owned += item.toString() + "\n";
        }
        owned += "\n";

        String cart = "\nShopping cart:\n\n";
        for (MusicItem item : shoppingCart){
            cart += item.toString() + "\n\n";
        }
        cart += "\n";

        String purchItems = "\nPurchased Items:\n\n";
        for (MusicItem item : purchasedItems){
            purchItems += item.toString() + "\n";
        }
        purchItems += "\n";



        return "\nUser: \n \n" + "ID: " + id + "\nEmail: " + email + "\nPassword: " + password + "\nRoles: " + roles + owned + cart + purchItems;
    }
}
