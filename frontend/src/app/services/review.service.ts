import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

export interface ReviewDto {
  id: number;
  reviewText: string;
  createdAt: string;
  updatedAt: string;
  landmarkId: number;
}

@Injectable({ providedIn: 'root' })
export class ReviewService {
  constructor(private http: HttpClient) {}

  getByLandmarkId(landmarkId: number): Observable<ReviewDto[]> {
    return this.http.get<ReviewDto[]>(`/api/reviews/search?landmarkId=${landmarkId}`);
  }
}
