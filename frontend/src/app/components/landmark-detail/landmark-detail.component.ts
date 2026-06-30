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
import { AuthService, UserDto } from '../../services/auth.service';
import { PersonService, PersonDto } from '../../services/person.service';
import { NotificationService } from '../../services/notification.service';
import { StarRatingComponent } from '../../components/star-rating/star-rating.component';

@Component({
  selector: 'app-landmark-detail',
  standalone: true,
  imports: [
    CommonModule,
    RouterModule,
    MatCardModule,
    MatButtonModule,
    MatDividerModule,
    StarRatingComponent,
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
    .review-header {
      display: flex;
      justify-content: space-between;
      align-items: center;
      margin-bottom: 4px;
    }
    .review-author {
      font-weight: 600;
      color: #212121;
    }
    .review-date {
      font-size: 12px;
      color: #888;
    }
    .review-text {
      margin: 4px 0 0 0;
      color: #424242;
    }
  `]
})
export class LandmarkDetailComponent implements OnInit {
  landmark: LandmarkDto | null = null;
  user: UserDto | null = null;
  newRating: number = 0;
  categoryName: string = '';
  reviews: ReviewDto[] = [];
  ratings: RatingDto[] = [];
  averageRating: number = 0;
  users: Map<number, string> = new Map();

  constructor(
    private route: ActivatedRoute,
    private landmarkService: LandmarkService,
    private categoryService: CategoryService,
    private reviewService: ReviewService,
    private ratingService: RatingService,
    private authService: AuthService,
    private personService: PersonService,
    private notificationService: NotificationService
  ) {}

  ngOnInit(): void {
    this.authService.currentUser$.subscribe({
      next: (user) => {
        this.user = user;
        console.log('👤 Текущий пользователь:', user);
      },
      error: (err) => console.error('Ошибка получения пользователя:', err)
    });

    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      const landmarkId = Number(id);
      this.loadLandmark(landmarkId);
      this.loadReviews(landmarkId);
      this.loadRatings(landmarkId);
      this.loadAllUsers();
    }
  }

  private loadLandmark(id: number): void {
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

  private loadReviews(landmarkId: number): void {
    this.reviewService.getByLandmarkId(landmarkId).subscribe({
      next: (data) => {
        this.reviews = data;
        console.log('Отзывы для достопримечательности', landmarkId, ':', data);
      },
      error: (err) => console.error('Ошибка загрузки отзывов:', err)
    });
  }

  private loadRatings(landmarkId: number): void {
    this.ratingService.getByLandmarkId(landmarkId).subscribe({
      next: (data) => {
        this.ratings = data;
        const total = data.reduce((sum, r) => sum + r.grade, 0);
        this.averageRating = data.length > 0 ? total / data.length : 0;
        console.log('Средняя оценка:', this.averageRating);
      },
      error: (err) => console.error('Ошибка загрузки оценок:', err)
    });
  }

  private loadAllUsers(): void {
    this.personService.getAll().subscribe({
      next: (users: PersonDto[]) => {
        users.forEach(user => this.users.set(user.id, user.name));
        console.log('Пользователи загружены:', this.users);
      },
      error: (err) => console.error('Ошибка загрузки пользователей:', err)
    });
  }

  getUserName(id: number): string {
    return this.users.get(id) || 'Неизвестно';
  }

  getStars(rating: number): string {
    const full = Math.floor(rating);
    const empty = 5 - full;
    return '⭐'.repeat(full) + '☆'.repeat(empty);
  }

  hasUserRated(): boolean {
    if (!this.user || !this.ratings.length) return false;
    return this.ratings.some(r => r.personId === this.user!.id);
  }

  submitRating(): void {
    if (!this.landmark || this.newRating === 0) {
      return;
    }

    if (!this.user) {
      this.notificationService.error('Войдите в систему, чтобы оценить место');
      return;
    }

    if (this.hasUserRated()) {
      this.notificationService.error('Вы уже оценили это место!');
      return;
    }

    this.ratingService.addRating({
      grade: this.newRating,
      landmarkId: this.landmark.id
    }).subscribe({
      next: () => {
        this.notificationService.success('Оценка поставлена!');
        this.newRating = 0;
        this.loadRatings(this.landmark!.id);
      },
      error: (err) => {
        console.error('Ошибка:', err);
        if (err.status === 400) {
          this.notificationService.error('Вы уже оценили это место!');
        }
      }
    });
  }
}
