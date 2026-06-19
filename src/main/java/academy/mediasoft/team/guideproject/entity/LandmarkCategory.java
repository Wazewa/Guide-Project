package academy.mediasoft.team.guideproject.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "LandmarkCategory")
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LandmarkCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "landmark_category_id", nullable = false)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @OneToMany(mappedBy = "category")
    private List<Landmark> landmarks;
}
