import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

export interface LandmarkDto {
  id: number;
  name: string;
  coordinates: string;
  description: string;
  address: string;
  landmarkCategoryId: number;
  categoryName: string;
  averageRating: number;
}

@Injectable({ providedIn: 'root' })
export class LandmarkService {
  constructor(private http: HttpClient) {}

  getAll(): Observable<LandmarkDto[]> {
    return this.http.get<LandmarkDto[]>('/api/landmarks');
  }

  getById(id: number): Observable<LandmarkDto> {
    return this.http.get<LandmarkDto>(`/api/landmarks/${id}`);
  }

  search(lat: number, lng: number, radius: number, limit: number = 10): Observable<LandmarkDto[]> {
    return this.http.get<LandmarkDto[]>(`/api/landmarks/search?latitude=${lat}&longitude=${lng}&radius=${radius}&limit=${limit}`);
  }
}
