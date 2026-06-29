import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { LandmarkService, LandmarkDto } from '../../services/landmark.service';
import { CategoryService } from '../../services/category.service';

@Component({
  selector: 'app-landmark-list',
  standalone: true,
  imports: [
    CommonModule,
    RouterModule,
    MatCardModule,
    MatButtonModule
  ],
  templateUrl: './landmark-list.component.html',
  styleUrls: ['./landmark-list.component.scss']
})
export class LandmarkListComponent implements OnInit {
  landmarks: LandmarkDto[] = [];
  categoryMap: Map<number, string> = new Map();

  constructor(
    private landmarkService: LandmarkService,
    private categoryService: CategoryService
  ) {}

  ngOnInit() {
    this.categoryService.getMap().subscribe({
      next: (map) => {
        this.categoryMap = map;
        console.log('Категории загружены:', map);
      },
      error: (err) => console.error('Ошибка загрузки категорий:', err)
    });

    this.landmarkService.getAll().subscribe({
      next: (data) => {
        this.landmarks = data;
        console.log('Достопримечательности загружены:', data);
      },
      error: (err) => console.error('Ошибка загрузки достопримечательностей:', err)
    });
  }

  getCategoryName(id: number): string {
    return this.categoryMap.get(id) || 'Неизвестно';
  }

  getStars(rating: number): string {
    const full = Math.round(rating);
    const empty = 5 - full;
    return '⭐'.repeat(full) + '☆'.repeat(empty);
  }
}
