import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable, of } from 'rxjs';
import { catchError, tap } from 'rxjs/operators';

export interface UserDto {
  id: number;
  name: string;
  surname: string;
  email: string;
}

export interface LoginRequest {
  email: string;
  password: string;
}

export interface RegisterRequest {
  name: string;
  surname: string;
  email: string;
  password: string;
}

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  // 1️⃣ BehaviorSubject для хранения состояния пользователя
  private currentUserSubject = new BehaviorSubject<UserDto | null>(null);
  public currentUser$ = this.currentUserSubject.asObservable();

  constructor(private http: HttpClient) {}

  // 2️⃣ Регистрация
  register(data: RegisterRequest): Observable<any> {
    return this.http.post('/api/persons/register', data);
  }

  // 3️⃣ Логин — сохраняем пользователя после успешного входа
  login(data: LoginRequest): Observable<any> {
    return this.http.post('/api/auth/login', data, { withCredentials: true }).pipe(
      tap(() => {
        this.loadCurrentUser();
      })
    );
  }

  // 4️⃣ Выход — очищаем пользователя
  logout(): Observable<any> {
    return this.http.post('/api/auth/logout', {}, { withCredentials: true }).pipe(
      tap(() => {
        this.currentUserSubject.next(null);
      })
    );
  }

  // 5️⃣ Загрузка текущего пользователя
  loadCurrentUser(): void {
    console.log('🔍 Загрузка пользователя');
    this.http.get<UserDto>('/api/auth/me', { withCredentials: true }).pipe(
      catchError((err) => {
        console.log('❌ Ошибка получения пользователя:', err.status);
        this.currentUserSubject.next(null);
        return of(null);
      })
    ).subscribe(user => {
      if (user) {
        console.log('✅ Пользователь получен:', user);
      } else {
        console.log('❌ Пользователь не найден');
      }
      this.currentUserSubject.next(user);
    });
  }

  // 6️⃣ Получение текущего пользователя (для компонентов)
  getCurrentUser(): Observable<UserDto | null> {
    return this.currentUser$;
  }
}
