import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

export interface ReviewDto {
  id: number;
  reviewText: string;
  createdAt: string;
  updatedAt: string;
  landmarkId: number;
  personId: number;
  personName: string
}

@Injectable({ providedIn: 'root' })
export class ReviewService {
  constructor(private http: HttpClient) {}

  getByLandmarkId(landmarkId: number): Observable<ReviewDto[]> {
    return this.http.get<ReviewDto[]>(`/api/reviews/search?landmarkId=${landmarkId}`);
  }

  addReview(data: { reviewText: string; landmarkId: number }): Observable<ReviewDto> {
    return this.http.post<ReviewDto>('/api/reviews', data);
  }

  updateReview(id: number, data: { reviewText: string }): Observable<ReviewDto> {
    return this.http.put<ReviewDto>(`/api/reviews/${id}`, data);
  }

  deleteReview(id: number): Observable<void> {
    return this.http.delete<void>(`/api/reviews/${id}`);
  }
}
