package hr.trio.realestatetracker.model;

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
@Table(name = "real_estate_type")
public class RealEstateType {

    @Id
    @SequenceGenerator(name = "real_estate_type_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "real_estate_type_id_seq", strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "type")
    private String type;

}
