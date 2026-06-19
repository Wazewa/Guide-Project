package academy.mediasoft.team.guideproject.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Landmark")
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Landmark {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "landmark_id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(nullable = false)
    private String coordinates;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String address;

    @ManyToOne
    @JoinColumn(name = "landmark_category_id")
    private LandmarkCategory category;
}
