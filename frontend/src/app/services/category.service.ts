import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

export interface CategoryDto {
  id: number;
  name: string;
}

@Injectable({ providedIn: 'root' })
export class CategoryService {
  constructor(private http: HttpClient) {}

  getAll(): Observable<CategoryDto[]> {
    return this.http.get<CategoryDto[]>('/api/categories');
  }

  getMap(): Observable<Map<number, string>> {
    return this.getAll().pipe(
      map(categories => {
        const map = new Map<number, string>();
        categories.forEach(cat => map.set(cat.id, cat.name));
        return map;
      })
    );
  }
}
