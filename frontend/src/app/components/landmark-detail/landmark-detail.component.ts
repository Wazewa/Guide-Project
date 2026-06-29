import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { MatDividerModule } from '@angular/material/divider';
import { LandmarkService, LandmarkDto } from '../../services/landmark.service';
import { CategoryService } from '../../services/category.service'

@Component({
  selector: 'app-landmark-detail',
  standalone: true,
  imports: [
    CommonModule,
    MatCardModule,
    MatButtonModule,
    MatDividerModule
  ],
  templateUrl: './landmark-detail.component.html',
  styles: [`
    .detail-container {
      max-width: 800px;
      margin: 20px auto;
      padding: 0 16px;
    }
    mat-card {
      padding: 20px;
    }
    .loading {
      text-align: center;
      margin-top: 50px;
    }
  `]
})
export class LandmarkDetailComponent implements OnInit {
  landmark: LandmarkDto | null = null;
  categoryName: string = '';

  constructor(
    private route: ActivatedRoute,
    private landmarkService: LandmarkService,
    private categoryService: CategoryService
  ) {}

  ngOnInit() {
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.landmarkService.getById(Number(id)).subscribe({
        next: (data) => {
          this.landmark = data;
          this.categoryService.getMap().subscribe({
            next: (map) => {
              this.categoryName = map.get(data.landmarkCategoryId) || 'Неизвестно';
            },
            error: (err) => console.error('Ошибка загрузки категории:', err)
          });
        },
        error: (err) => console.error('Ошибка загрузки деталей:', err)
      });
    }
  }
}
