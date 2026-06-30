import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, RouterModule } from '@angular/router';
import { MatCardModule } from '@angular/material/card';
import { FormsModule } from '@angular/forms';
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
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
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
    MatFormFieldModule,
    MatInputModule,
    FormsModule,
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
    .hint-error {
      color: #ff5252 !important;
      font-weight: 500;
    }
    .review-form-card {
      margin-bottom: 20px;
    }
    .full-width {
      width: 100%;
    }
    .review-actions {
      display: flex;
      justify-content: flex-end;
      gap: 8px;
      margin-top: 8px;
    }
  `]
})
export class LandmarkDetailComponent implements OnInit {
  landmark: LandmarkDto | null = null;
  user: UserDto | null = null;
  newRating: number = 0;
  newReviewText: string = '';
  categoryName: string = '';
  reviews: ReviewDto[] = [];
  ratings: RatingDto[] = [];
  editingReviewId: number | null = null;
  editingReviewText: string = '';
  averageRating: number = 0;
  users: Map<number, string> = new Map();
  userReviewIds: Set<number> = new Set();

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
        // После получения пользователя обновляем ID отзывов
        this.updateUserReviewIds();
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
        // ✅ Обновляем ID отзывов пользователя по имени (без personId)
        if (this.user) {
          const fullName = `${this.user.name} ${this.user.surname}`;
          this.userReviewIds = new Set(
            data
              .filter(r => r.personName === fullName)
              .map(r => r.id)
          );
        }
        console.log('Отзывы для достопримечательности', landmarkId, ':', data);
        console.log('ID отзывов пользователя:', this.userReviewIds);
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
        console.log('Все оценки:', this.ratings);
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

  // ✅ Обновляем ID отзывов пользователя по имени
  private updateUserReviewIds(): void {
    if (!this.user || !this.reviews.length) {
      this.userReviewIds = new Set();
      return;
    }

    const fullName = `${this.user.name} ${this.user.surname}`;

    this.userReviewIds = new Set(
      this.reviews
        .filter(r => r.personName === fullName)
        .map(r => r.id)
    );

    console.log('Обновлены ID отзывов пользователя:', {
      fullName: fullName,
      userReviewIds: this.userReviewIds,
      totalReviews: this.reviews.length
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

  // ✅ Проверка, ставил ли пользователь оценку (по имени)
  hasUserRated(): boolean {
    if (!this.user || !this.ratings.length) return false;

    // Находим ID пользователя по имени
    let userId: number | null = null;
    for (const [id, name] of this.users) {
      if (name === this.user!.name) {
        userId = id;
        break;
      }
    }

    if (userId === null) return false;

    // Проверяем, есть ли оценка от этого пользователя
    return this.ratings.some(r => r.personId === userId);
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

    console.log('Отправка оценки:', {
      grade: this.newRating,
      landmarkId: this.landmark.id,
      userName: this.user.name
    });

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
          const errorMessage = err.error?.message || err.message || '';
          if (errorMessage.includes('уже оценили') || errorMessage.includes('уже есть')) {
            this.notificationService.error('Вы уже оценили это место!');
          } else {
            this.notificationService.error(errorMessage || 'Ошибка при добавлении оценки');
          }
        } else if (err.status === 500) {
          this.notificationService.error('Ошибка сервера. Возможно, вы уже оценили это место');
        } else {
          this.notificationService.error('Ошибка при добавлении оценки');
        }
      }
    });
  }

  hasUserReviewed(): boolean {
    if (!this.user || !this.reviews.length) return false;
    return this.userReviewIds.size > 0;
  }

  submitReview(): void {
    if (!this.landmark || !this.newReviewText || this.newReviewText.trim().length < 30) {
      this.notificationService.error('Отзыв должен быть не менее 30 символов');
      return;
    }

    if (!this.user) {
      this.notificationService.error('Войдите в систему, чтобы оставить отзыв');
      return;
    }

    if (this.hasUserReviewed()) {
      this.notificationService.error('Вы уже оставили отзыв на это место');
      return;
    }

    console.log('Отправка отзыва:', {
      reviewText: this.newReviewText.trim(),
      landmarkId: this.landmark.id,
      userName: this.user.name
    });

    this.reviewService.addReview({
      reviewText: this.newReviewText.trim(),
      landmarkId: this.landmark.id
    }).subscribe({
      next: () => {
        this.notificationService.success('Отзыв добавлен!');
        this.newReviewText = '';
        this.loadReviews(this.landmark!.id);
      },
      error: (err) => {
        console.error('Ошибка:', err);
        if (err.status === 400) {
          const errorMessage = err.error?.message || err.message || '';
          if (errorMessage.includes('уже оставили отзыв') || errorMessage.includes('уже есть')) {
            this.notificationService.error('Вы уже оставили отзыв на это место');
          } else if (errorMessage.includes('от 30 до 1000')) {
            this.notificationService.error('Отзыв должен быть от 30 до 1000 символов');
          } else {
            this.notificationService.error(errorMessage || 'Ошибка при добавлении отзыва');
          }
        } else if (err.status === 500) {
          this.notificationService.error('Вы уже оставили отзыв!');
        } else {
          this.notificationService.error('Ошибка при добавлении отзыва');
        }
      }
    });
  }

  startEdit(review: ReviewDto): void {
    this.editingReviewId = review.id;
    this.editingReviewText = review.reviewText;
  }

  cancelEdit(): void {
    this.editingReviewId = null;
    this.editingReviewText = '';
  }

  saveEdit(): void {
    if (!this.editingReviewId || !this.editingReviewText || this.editingReviewText.trim().length < 30) {
      this.notificationService.error('Отзыв должен быть не менее 30 символов');
      return;
    }

    this.reviewService.updateReview(this.editingReviewId, {
      reviewText: this.editingReviewText.trim()
    }).subscribe({
      next: () => {
        this.notificationService.success('Отзыв обновлён!');
        this.cancelEdit();
        this.loadReviews(this.landmark!.id);
      },
      error: (err) => {
        console.error('Ошибка:', err);
        this.notificationService.error('Ошибка при обновлении отзыва');
      }
    });
  }

  deleteReview(id: number): void {
    this.notificationService.confirm('Вы уверены, что хотите удалить отзыв?').subscribe({
      next: (confirmed) => {
        if (confirmed) {
          this.reviewService.deleteReview(id).subscribe({
            next: () => {
              this.notificationService.success('Отзыв удалён!');
              this.loadReviews(this.landmark!.id);
            },
            error: (err) => {
              console.error('Ошибка:', err);
              this.notificationService.error('Ошибка при удалении отзыва');
            }
          });
        }
      }
    });
  }

  isOwnReview(review: ReviewDto): boolean {
    if (!this.user) return false;
    const fullName = `${this.user.name} ${this.user.surname}`;
    console.log('isOwnReview:', {
      reviewName: review.personName,
      fullName: fullName,
      result: review.personName === fullName
    });

    return review.personName === fullName;
  }
}
