package hr.trio.realestatetracker.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "role")
public class Role implements Serializable {

    @Id
    @SequenceGenerator(name = "role_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "role_id_seq", strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "name", length = 50, nullable = false)
    private String name;

}