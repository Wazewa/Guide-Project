import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, RouterModule } from '@angular/router';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { MatDividerModule } from '@angular/material/divider';
import { MatListModule } from '@angular/material/list';
import { LandmarkService, LandmarkDto } from '../../services/landmark.service';
import { CategoryService } from '../../services/category.service';
import { ReviewService, ReviewDto } from '../../services/review.service';
import { RatingService, RatingDto } from '../../services/rating.service';

@Component({
  selector: 'app-landmark-detail',
  standalone: true,
  imports: [
    CommonModule,
    RouterModule,
    MatCardModule,
    MatButtonModule,
    MatDividerModule,
    MatListModule
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
      margin-bottom: 20px;
    }
    .loading {
      text-align: center;
      margin-top: 50px;
    }
    .review-item {
      margin-bottom: 16px;
      padding: 12px;
      background: #f5f5f5;
      border-radius: 8px;
    }
    .review-date {
      font-size: 12px;
      color: #888;
    }
  `]
})
export class LandmarkDetailComponent implements OnInit {
  landmark: LandmarkDto | null = null;
  categoryName: string = '';
  reviews: ReviewDto[] = [];
  averageRating: number = 0;

  constructor(
    private route: ActivatedRoute,
    private landmarkService: LandmarkService,
    private categoryService: CategoryService,
    private reviewService: ReviewService,
    private ratingService: RatingService
  ) {}

  ngOnInit() {
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      const landmarkId = Number(id);
      this.loadLandmark(landmarkId);
      this.loadReviews(landmarkId);
      this.loadRatings(landmarkId);
    }
  }

  private loadLandmark(id: number) {
    this.landmarkService.getById(id).subscribe({
      next: (data) => {
        this.landmark = data;
        this.categoryService.getMap().subscribe({
          next: (map) => {
            this.categoryName = map.get(data.landmarkCategoryId) || 'Неизвестно';
          }
        });
      },
      error: (err) => console.error('Ошибка загрузки деталей:', err)
    });
  }

  private loadReviews(landmarkId: number) {
    this.reviewService.getByLandmarkId(landmarkId).subscribe({
      next: (data) => {
        this.reviews = data;
        console.log('Отзывы для достопримечательности', landmarkId, ':', data);
      },
      error: (err) => console.error('Ошибка загрузки отзывов:', err)
    });
  }

  private loadRatings(landmarkId: number) {
    this.ratingService.getByLandmarkId(landmarkId).subscribe({
      next: (data) => {
        const total = data.reduce((sum, r) => sum + r.grade, 0);
        this.averageRating = data.length > 0 ? total / data.length : 0;
        console.log('Средняя оценка:', this.averageRating);
      },
      error: (err) => console.error('Ошибка загрузки оценок:', err)
    });
  }

  getStars(rating: number): string {
    const full = Math.floor(rating);
    const empty = 5 - full;
    return '⭐'.repeat(full) + '☆'.repeat(empty);
  }
}
