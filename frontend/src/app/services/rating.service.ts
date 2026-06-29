import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

export interface RatingDto {
  id: number;
  grade: number;
  createdAt: string;
  landmarkId: number;
}

@Injectable({ providedIn: 'root' })
export class RatingService {
  constructor(private http: HttpClient) {}

  getByLandmarkId(landmarkId: number): Observable<RatingDto[]> {
    return this.http.get<RatingDto[]>(`/api/ratings?landmarkId=${landmarkId}`);
  }
}
