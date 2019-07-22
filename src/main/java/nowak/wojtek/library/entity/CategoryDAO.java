package nowak.wojtek.library.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "category_dao")
//        indexes = {@Index(name = "id", columnList = "id", unique = true),
//                @Index(name = "name", columnList = "name", unique = false)})
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDAO {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotNull
    private String name;

    public String toString() {
        return name;
    }

}

