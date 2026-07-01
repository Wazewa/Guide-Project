import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { LandmarkService, LandmarkDto } from '../../services/landmark.service';
import { CategoryService } from '../../services/category.service';

@Component({
  selector: 'app-landmark-list',
  standalone: true,
  imports: [
    CommonModule,
    RouterModule,
    FormsModule,
    MatCardModule,
    MatButtonModule,
    MatIconModule
  ],
  templateUrl: './landmark-list.component.html',
  styleUrls: ['./landmark-list.component.scss']
})
export class LandmarkListComponent implements OnInit {
  landmarks: LandmarkDto[] = [];
  filteredLandmarks: LandmarkDto[] = [];
  categoryMap: Map<number, string> = new Map();
  searchQuery: string = '';

  constructor(
    private landmarkService: LandmarkService,
    private categoryService: CategoryService
  ) {}

  ngOnInit() {
    this.categoryService.getMap().subscribe({
      next: (map) => {
        this.categoryMap = map;
      },
      error: (err) => console.error('Ошибка загрузки категорий:', err)
    });

    this.landmarkService.getAll().subscribe({
      next: (data) => {
        this.landmarks = data;
        this.filteredLandmarks = data;
      },
      error: (err) => console.error('Ошибка загрузки достопримечательностей:', err)
    });
  }

  applyFilters(): void {
    let result = [...this.landmarks];

    if (this.searchQuery.trim()) {
      const query = this.searchQuery.toLowerCase().trim();
      result = result.filter(l =>
        l.name.toLowerCase().includes(query)
      );
    }

    this.filteredLandmarks = result;
  }

  clearSearch(): void {
    this.searchQuery = '';
    this.applyFilters();
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
