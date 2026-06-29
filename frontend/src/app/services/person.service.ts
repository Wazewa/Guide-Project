import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

export interface PersonDto {
  id: number;
  name: string;
  surname: string;
  email: string;
}

@Injectable({
  providedIn: 'root'
})
export class PersonService {
  constructor(private http: HttpClient) {}

  getAll(): Observable<PersonDto[]> {
    return this.http.get<PersonDto[]>('/api/persons');
  }

  getById(id: number): Observable<PersonDto> {
    return this.http.get<PersonDto>(`/api/persons/${id}`);
  }
}
