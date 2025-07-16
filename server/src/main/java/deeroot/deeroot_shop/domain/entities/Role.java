package deeroot.deeroot_shop.domain.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.aspectj.weaver.GeneratedReferenceTypeDelegate;


@Entity
@Table(name = "roles")
@Getter
@Setter
//@NoArgsConstructor
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String name;
/*
    public Role(String name){
        this.name = name;
    }

 */

}
