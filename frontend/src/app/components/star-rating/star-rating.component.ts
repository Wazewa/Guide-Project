import { Component, EventEmitter, Input, Output } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-star-rating',
  standalone: true,
  imports: [CommonModule],
  template: `
    <div class="stars">
      <span
        *ngFor="let star of stars; let i = index"
        (mouseenter)="hoverRating = i + 1"
        (mouseleave)="hoverRating = null"
        (click)="setRating(i + 1)"
        class="star"
        [class.filled]="isFilled(i + 1)"
      >
        ⭐
      </span>
    </div>
  `,
  styles: [`
    .stars {
      display: flex;
      gap: 4px;
      cursor: pointer;
    }
    .star {
      font-size: 28px;
      user-select: none;
      transition: transform 0.1s;
      opacity: 0.3;
    }
    .star:hover {
      transform: scale(1.2);
    }
    .star.filled {
      opacity: 1;
    }
  `]
})
export class StarRatingComponent {
  @Input() rating: number = 0;
  @Output() ratingChange = new EventEmitter<number>();

  hoverRating: number | null = null;

  get stars(): number[] {
    return [1, 2, 3, 4, 5];
  }

  get currentRating(): number {
    return this.hoverRating ?? this.rating;
  }

  isFilled(star: number): boolean {
    return this.currentRating >= star;
  }

  setRating(value: number): void {
    if (this.rating === value) {
      this.rating = 0;
    } else {
      this.rating = value;
    }
    this.ratingChange.emit(this.rating);
  }
}
